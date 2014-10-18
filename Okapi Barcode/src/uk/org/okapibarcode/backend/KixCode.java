package uk.org.okapibarcode.backend;

import java.util.Locale;
import java.awt.Rectangle;

/**
 *
 * @author Robin Stuart <rstuart114@gmail.com>
 */
public class KixCode extends Symbol {

    /* Handles Dutch Post TNT KIX symbols */
    /* The same as RM4SCC but without check digit */
    /* Specification at http://www.tntpost.nl/zakelijk/klantenservice/downloads/kIX_code/download.aspx */

    private String[] RoyalTable = {
        "TTFF", "TDAF", "TDFA", "DTAF", "DTFA", "DDAA", "TADF", "TFTF", "TFDA", 
        "DATF", "DADA", "DFTA", "TAFD", "TFAD", "TFFT", "DAAD", "DAFT", "DFAT", 
        "ATDF", "ADTF", "ADDA", "FTTF", "FTDA", "FDTA", "ATFD", "ADAD", "ADFT", 
        "FTAD", "FTFT", "FDAT", "AADD", "AFTD", "AFDT", "FATD", "FADT", "FFTT"
    };

    private char[] krSet = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 
        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    @Override
    public boolean encode() {
        String dest;
        int i;

        content = content.toUpperCase(Locale.ENGLISH);
        //if(!(content.matches("[0-9][A-Z]+?"))) {
        //    error_msg = "Invalid characters in data";
        //    return false;
        //}
        dest = "";

        for (i = 0; i < content.length(); i++) {
            dest += RoyalTable[positionOf(content.charAt(i), krSet)];
        }

        readable = "";
        pattern = new String[1];
        pattern[0] = dest;
        row_count = 1;
        row_height = new int[1];
        row_height[0] = -1;
        plotSymbol();
        return true;
    }

    @Override
    public void plotSymbol() {
        int xBlock;
        int x, y, w, h;

        rect.clear();
        x = 0;
        w = 1;
        y = 0;
        h = 0;
        for (xBlock = 0; xBlock < pattern[0].length(); xBlock++) {
            switch (pattern[0].charAt(xBlock)) {
            case 'A':
                y = 0;
                h = 5;
                break;
            case 'D':
                y = 3;
                h = 5;
                break;
            case 'F':
                y = 0;
                h = 8;
                break;
            case 'T':
                y = 3;
                h = 2;
                break;
            }

            Rectangle thisrect = new Rectangle(x, y, w, h);
            rect.add(thisrect);

            x += 2.0;
        }
        symbol_width = pattern[0].length() * 3;
        symbol_height = 8;
    }
}
