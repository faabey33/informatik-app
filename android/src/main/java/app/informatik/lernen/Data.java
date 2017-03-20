package app.informatik.lernen;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fabia on 2/13/2017.
 */

public class Data {

    public static List<Item> items = new ArrayList<>();

    public static int getSize(int dataIndex) {
        return 12;
    }

    private static int[] barColors = {1,1,1};
    public static void loadColors(Context c) {
        barColors[0] = c.getResources().getColor(R.color.bar_1);
        barColors[1] = c.getResources().getColor(R.color.bar_2);
        barColors[2] = c.getResources().getColor(R.color.bar_3);
    }

    public static int[] getBarColors() {
        return barColors;
    }

    public static void updateProgress(int index) {
        Item item = items.get(index);
        int learned = 0;
        for (int i = 0; i < item.getThemen().size(); i++) {
            if (item.getThemen().get(i).getCompleteCode() == 1) {
                learned++;
            }
        }
        items.get(index).setProgress(learned);
    }

}
