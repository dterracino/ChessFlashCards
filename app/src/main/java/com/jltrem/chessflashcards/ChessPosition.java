package com.jltrem.chessflashcards;

public class ChessPosition {
    public Integer id;
    public String description;
    public String fen;

    public enum Piece {
        NONE,
        PAWN,
        ROOK,
        KNIGHT,
        BISHOP,
        QUEEN,
        KING
    }

    public enum PieceColor{
        NONE,
        WHITE,
        BLACK
    }

    private char[][] _position = new char[8][8];
    private boolean _whiteActive;

    public ChessPosition() {
        id = -1;
        description = "";
        fen = "";
    }

    public boolean ParseFen() {
        FenParser parser = new FenParser();
        parser.Parse();
        return parser.isValid();
    }

    public boolean isWhiteActive() {
        return _whiteActive;
    }

    public PieceColor getPieceColor(int rowNdx, int colNdx) {

        if (getPiece(rowNdx, colNdx) == Piece.NONE){
            return PieceColor.NONE;
        }

        char c = _position[rowNdx][colNdx];
        return Character.isUpperCase(c) ? PieceColor.WHITE : PieceColor.BLACK;
    }

    public Piece getPiece(int rowNdx, int colNdx) {
        char c = _position[rowNdx][colNdx];

        switch (c){
            case 'p':
            case 'P':
                return Piece.PAWN;
            case 'r':
            case 'R':
                return Piece.ROOK;
            case 'n':
            case 'N':
                return Piece.KNIGHT;
            case 'b':
            case 'B':
                return Piece.BISHOP;
            case 'q':
            case 'Q':
                return Piece.QUEEN;
            case 'k':
            case 'K':
                return Piece.KING;
            default:
                return Piece.NONE;
        }
    }

    private class FenParser {

        public boolean isValid() {
            return _valid;
        }

        private int _rowNdx;
        private int _colNdx;

        private boolean _valid;

        public void Parse() {
            _valid = false;

            String[] parts = fen.split(" ");
            if (parts.length == 6) {
                String[] rows = parts[0].split("/");

                ProcessFenRows(rows);

                _whiteActive = (parts[1].equals("w"));
            }
        }

        private void ProcessFenRows(String[] rows) {
            if (rows.length == 8) {

                // ... assume valid unless parsing fails
                _valid = true;

                for (_rowNdx = 0; _rowNdx < 8; _rowNdx++) {

                    _colNdx = 0;

                    int rowChars = rows[_rowNdx].length();
                    for (int cNdx = 0; cNdx < rowChars; cNdx++) {
                        char c = rows[_rowNdx].charAt(cNdx);
                        ProcessFenCharacter(c);
                    }

                    if (!_valid) {
                        break;
                    }
                }
            }
        }

        private void ProcessFenCharacter(char c) {
            if (Character.isLetter(c)) {
                setSquare(c);
            } else if (Character.isDigit(c)) {
                int numEmpty = Character.getNumericValue(c);
                for (int skipNdx = 0; skipNdx < numEmpty; skipNdx++) {
                    setSquare(' ');
                }
            } else {
                _valid = false;
            }
        }

        private void setSquare(char c) {
            if (_colNdx < 8) {
                _position[_rowNdx][_colNdx] = c;
                _colNdx++;
            } else {
                _valid = false;
            }
        }

    }

}
