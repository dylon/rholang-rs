use rholang_parser::errors::{ErrorKind, ParseResult, ParserError, SourcePosition};

#[test]
fn test_source_position_display() {
    let pos = SourcePosition {
        line: 10,
        column: 20,
    };
    assert_eq!(format!("{}", pos), "line 10, column 20");
}

#[test]
fn test_parser_error_creation() {
    // Test parsing_error
    let pos = SourcePosition {
        line: 5,
        column: 10,
    };
    let err =
        ParserError::parsing_error("Parse error", Some(pos.clone()), Some("code".to_string()));

    assert_eq!(err.kind, ErrorKind::ParsingError);
    assert_eq!(err.message, "Parse error");
    assert_eq!(err.position.clone().unwrap().line, 5);
    assert_eq!(err.position.clone().unwrap().column, 10);
    assert_eq!(err.source.clone().unwrap(), "code");

    // Test tree_sitter_error
    let err =
        ParserError::tree_sitter_error("Tree-sitter error", Some(pos), Some("code".to_string()));

    assert_eq!(err.kind, ErrorKind::TreeSitterError);
    assert_eq!(err.message, "Tree-sitter error");

    // Test other_error
    let err = ParserError::other_error("Other error");

    assert_eq!(err.kind, ErrorKind::OtherError);
    assert_eq!(err.message, "Other error");
    assert!(err.position.is_none());
    assert!(err.source.is_none());
}

#[test]
fn test_parser_error_display() {
    // Test display for ParsingError
    let pos = SourcePosition {
        line: 5,
        column: 10,
    };
    let err = ParserError::parsing_error("Parse error", Some(pos), Some("code".to_string()));

    assert_eq!(
        format!("{}", err),
        "Parsing error: Parse error at line 5, column 10\nSource: code"
    );

    // Test display for TreeSitterError
    let err = ParserError::tree_sitter_error("Tree-sitter error", None, None);

    assert_eq!(format!("{}", err), "Tree-sitter error: Tree-sitter error");

    // Test display for OtherError
    let err = ParserError::other_error("Other error");

    assert_eq!(format!("{}", err), "Error: Other error");
}

#[test]
fn test_parse_result_creation() {
    // Test success
    let result: ParseResult<String> = ParseResult::success("Success".to_string());

    assert!(result.is_success());
    assert!(!result.is_error());

    // Test error
    let err = ParserError::other_error("Error");
    let result: ParseResult<String> = ParseResult::error(err);

    assert!(!result.is_success());
    assert!(result.is_error());
}

#[test]
fn test_parse_result_map() {
    // Test map on success
    let result: ParseResult<String> = ParseResult::success("42".to_string());
    let mapped: ParseResult<i32> = result.map(|s| s.parse::<i32>().unwrap());

    match mapped {
        ParseResult::Success(value) => assert_eq!(value, 42),
        ParseResult::Error(_) => panic!("Expected success"),
    }

    // Test map on error
    let err = ParserError::other_error("Error");
    let result: ParseResult<String> = ParseResult::error(err);
    let mapped: ParseResult<i32> = result.map(|s| s.parse::<i32>().unwrap());

    assert!(mapped.is_error());
}

#[test]
fn test_parse_result_unwrap() {
    // Test unwrap on success
    let result: ParseResult<String> = ParseResult::success("Success".to_string());

    assert_eq!(result.unwrap(), "Success");
}

#[test]
#[should_panic(expected = "Called unwrap on an error result")]
fn test_parse_result_unwrap_panic() {
    // Test unwrap on error (should panic)
    let err = ParserError::other_error("Error");
    let result: ParseResult<String> = ParseResult::error(err);

    result.unwrap();
}

#[test]
fn test_parse_result_unwrap_err() {
    // Test unwrap_err on error
    let err = ParserError::other_error("Error");
    let result: ParseResult<String> = ParseResult::error(err);

    let unwrapped = result.unwrap_err();
    assert_eq!(unwrapped.message, "Error");
}

#[test]
#[should_panic(expected = "Called unwrap_err on a success result")]
fn test_parse_result_unwrap_err_panic() {
    // Test unwrap_err on success (should panic)
    let result: ParseResult<String> = ParseResult::success("Success".to_string());

    result.unwrap_err();
}

#[test]
fn test_parse_result_from_parser_error() {
    // Test From<ParserError> for ParseResult<T>
    let err = ParserError::other_error("Error");
    let result: ParseResult<String> = err.into();

    assert!(result.is_error());
}
