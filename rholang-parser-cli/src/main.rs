use anyhow::{Context, Result};
use clap::{Parser, Subcommand};
use rholang_parser::RholangParser;
use serde::Serialize;
use std::fs;
use std::io::{self, Read};
use std::path::PathBuf;

#[derive(Parser)]
#[command(author, version, about, long_about = None)]
struct Cli {
    #[command(subcommand)]
    command: Commands,
}

#[derive(Subcommand)]
enum Commands {
    /// Parse Rholang code and output a JSON representation of the parse tree
    Parse {
        /// Input file (if not provided, reads from stdin)
        #[arg(short, long)]
        input: Option<PathBuf>,

        /// Pretty-print the output JSON
        #[arg(short, long)]
        pretty: bool,
    },

    /// Check if the code is valid Rholang
    Check {
        /// Input file (if not provided, reads from stdin)
        #[arg(short, long)]
        input: Option<PathBuf>,
    },
}

#[derive(Serialize)]
struct ParseResult {
    valid: bool,
    tree: Option<String>,
    error: Option<String>,
}

fn main() -> Result<()> {
    let cli = Cli::parse();

    match &cli.command {
        Commands::Parse { input, pretty } => {
            let code = read_input(input)?;
            let mut parser = RholangParser::new().context("Failed to create parser")?;
            
            let result = match parser.get_tree_string(&code) {
                rholang_parser::errors::ParseResult::Success(tree) => ParseResult {
                    valid: true,
                    tree: Some(tree),
                    error: None,
                },
                rholang_parser::errors::ParseResult::Error(err) => ParseResult {
                    valid: false,
                    tree: None,
                    error: Some(format!("{}", err)),
                },
            };

            if *pretty {
                println!("{}", serde_json::to_string_pretty(&result)?);
            } else {
                println!("{}", serde_json::to_string(&result)?);
            }
        }
        Commands::Check { input } => {
            let code = read_input(input)?;
            let mut parser = RholangParser::new().context("Failed to create parser")?;
            
            let valid = parser.is_valid(&code);
            let result = ParseResult {
                valid,
                tree: None,
                error: None,
            };

            println!("{}", serde_json::to_string(&result)?);
        }
    }

    Ok(())
}

fn read_input(input: &Option<PathBuf>) -> Result<String> {
    match input {
        Some(path) => fs::read_to_string(path).context("Failed to read input file"),
        None => {
            let mut buffer = String::new();
            io::stdin()
                .read_to_string(&mut buffer)
                .context("Failed to read from stdin")?;
            Ok(buffer)
        }
    }
}