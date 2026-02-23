import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame
{
    JPanel mainPnl, boardPnl, ctrlPnl;
    JButton quitBtn;
    TicTacToeButton[][] board;

    boolean finished = false;
    boolean playing = true;
    String player = "X";
    int moveCnt = 0;
    int row = -1;
    int col = -1;
    final int ROW = 3;
    final int COL = 3;
    final int MOVES_FOR_WIN = 5;
    final int MOVES_FOR_TIE = 7;

    public TicTacToeFrame()
    {
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());
        add(mainPnl);

        setTitle("Tic Tac Toe Game");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        createBoardPanel();
        createControlPanel();
    }

    public void createBoardPanel()
    {
        boardPnl = new JPanel();
        boardPnl.setLayout(new GridLayout(3, 3));
        board = new TicTacToeButton[3][3];
        for (int row = 0; row < 3; row++)
        {
            for (int col = 0; col < 3; col++)
            {
                board[row][col] = new TicTacToeButton(row, col);
                board[row][col].setText(" ");
                board[row][col].setFont(new Font("Sans-serif", Font.BOLD, 48));
                boardPnl.add(board[row][col]);
            }
        }
        ActionListener buttonListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Object move = e.getSource();
                for (int row = 0; row < 3; row++)
                {
                    for (int col = 0; col < 3; col++)
                    {
                        if (move == board[row][col])
                        {
                            if (isValidMove(row, col))
                            {
                                board[row][col].setText(player);
                                moveCnt++;
                                checkWin();
                                if(player.equals("X"))
                                {
                                    player = "O";
                                }
                                else
                                {
                                    player = "X";
                                }
                            }
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Invalid move!");
                            }
                        }
                    }
                }
            }
        };

        board[0][0].addActionListener(buttonListener);
        board[0][1].addActionListener(buttonListener);
        board[0][2].addActionListener(buttonListener);
        board[1][0].addActionListener(buttonListener);
        board[1][1].addActionListener(buttonListener);
        board[1][2].addActionListener(buttonListener);
        board[2][0].addActionListener(buttonListener);
        board[2][1].addActionListener(buttonListener);
        board[2][2].addActionListener(buttonListener);

        mainPnl.add(boardPnl, BorderLayout.CENTER);
    }

    public void createControlPanel()
    {
        ctrlPnl = new JPanel();
        quitBtn = new JButton("Quit");
        quitBtn.addActionListener((ActionEvent e) -> {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION)
            {
                System.exit(0);
            }
        });
        ctrlPnl.add(quitBtn);
        mainPnl.add(ctrlPnl, BorderLayout.SOUTH);
    }

    public void resetGame()
    {
        clearBoard();
        player = "O";
        moveCnt = 0;
    }

    public void checkWin()
    {
        if(moveCnt >= MOVES_FOR_WIN)
        {
            if(isWin(player))
            {
                JOptionPane.showMessageDialog(null, "Player " + player + " wins!");
                int playMore = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Confirm Play Again", JOptionPane.YES_NO_OPTION);
                if (playMore == JOptionPane.YES_OPTION)
                {
                    resetGame();
                }
            }
        }
        if(moveCnt >= MOVES_FOR_TIE)
        {
            if(isTie())
            {
                JOptionPane.showMessageDialog(null, "It's a tie!");
                int playMore = JOptionPane.showConfirmDialog(null, "Do you want to play again?", "Confirm Play Again", JOptionPane.YES_NO_OPTION);
                if (playMore == JOptionPane.YES_OPTION)
                {
                    resetGame();
                }
            }
        }
    }

    public void clearBoard()
    {
        // sets all the board elements to a space
        for(int row=0; row < ROW; row++)
        {
            for(int col=0; col < COL; col++)
            {
                board[row][col].setText(" ");
            }
        }
    }

    private boolean isValidMove(int row, int col)
    {
        boolean retVal = false;
        String tileTxt = board[row][col].getText();
        if (tileTxt.equals(" "))
        {
            retVal = true;
        }

        return retVal;

    }
    private boolean isWin(String player)
    {
        if(isColWin(player) || isRowWin(player) || isDiagonalWin(player))
        {
            return true;
        }

        return false;
    }
    private boolean isColWin(String player)
    {
        // checks for a col win for specified player
        for(int col=0; col < COL; col++)
        {
            String tileTxt = board[0][col].getText();
            String tileTxt2 = board[1][col].getText();
            String tileTxt3 = board[2][col].getText();

            if(tileTxt.equals(player) &&
                    tileTxt2.equals(player) &&
                    tileTxt3.equals(player))
            {
                return true;
            }
        }
        return false; // no col win
    }
    private boolean isRowWin(String player)
    {
        // checks for a row win for the specified player
        for(int row=0; row < ROW; row++)
        {
            String tileTxt = board[row][0].getText();
            String tileTxt2 = board[row][1].getText();
            String tileTxt3 = board[row][2].getText();

            if(tileTxt.equals(player) &&
                    tileTxt2.equals(player) &&
                    tileTxt3.equals(player))
            {
                return true;
            }
        }
        return false; // no row win
    }
    private boolean isDiagonalWin(String player)
    {
        // checks for a diagonal win for the specified player

        String tileTxt = board[0][0].getText();
        String tileTxt2 = board[1][1].getText();
        String tileTxt3 = board[2][2].getText();
        String tileTxt4 = board[0][2].getText();
        String tileTxt5 = board[2][0].getText();

        if(tileTxt.equals(player) &&
                tileTxt2.equals(player) &&
                tileTxt3.equals(player) )
        {
            return true;
        }
        if(tileTxt4.equals(player) &&
                tileTxt2.equals(player) &&
                tileTxt5.equals(player) )
        {
            return true;
        }
        return false;
    }

    // checks for a tie before board is filled.
    // check for the win first to be efficient
    private boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for(int row=0; row < ROW; row++)
        {
            String tile = board[row][0].getText();
            String tile2 = board[row][1].getText();
            String tile3 = board[row][2].getText();

            if(tile.equals("X") ||
                    tile2.equals("X") ||
                    tile3.equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if(tile.equals("O") ||
                    tile2.equals("O") ||
                    tile3.equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for(int col=0; col < COL; col++)
        {
            String tile = board[0][col].getText();
            String tile2 = board[1][col].getText();
            String tile3 = board[2][col].getText();

            if(tile.equals("X") ||
                    tile2.equals("X") ||
                    tile3.equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if(tile.equals("O") ||
                    tile2.equals("O") ||
                    tile3.equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if(! (xFlag && oFlag) )
            {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        String tile = board[0][0].getText();
        String tile2 = board[1][1].getText();
        String tile3 = board[2][2].getText();

        if(tile.equals("X") ||
                tile2.equals("X") ||
                tile3.equals("X") )
        {
            xFlag = true;
        }
        if(tile.equals("O") ||
                tile2.equals("O") ||
                tile3.equals("O") )
        {
            oFlag = true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        String tile4 = board[0][2].getText();
        String tile5 = board[2][0].getText();

        if(tile4.equals("X") ||
                tile2.equals("X") ||
                tile5.equals("X") )
        {
            xFlag =  true;
        }
        if(tile4.equals("O") ||
                tile2.equals("O") ||
                tile5.equals("O") )
        {
            oFlag =  true;
        }
        if(! (xFlag && oFlag) )
        {
            return false; // No tie can still have a diag win
        }

        // Checked every vector so I know I have a tie
        return true;
    }
}
