package app.informatik.lernen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;

import java.util.List;

import at.grabner.circleprogress.CircleProgressView;

/**
 * Created by fabia on 2/19/2017.
 */

public class CustomItemView extends LinearLayout {

    private static int totalItemCount;

    private int colorBackground;
    private int colorBackgroundDark;

    private List<Thema> themen;
    private String title;
    private String desc;
    private int themaID;
    private int itemID;

    private CircleProgressView circleView_1;
    private TextView items_1;
    private TextView title_1;
    private TextView desc_1;
    private TextView button_themen;
    private ExpandableLayout expandableLayout;
    private RelativeLayout button;

    public CustomItemView(Context context) {
        super(context);
        init(context);
    }

    public CustomItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void updateProgress() {
        circleView_1.setValue(Data.items.get(itemID).getProgress());
    }

    public CustomItemView(Context context, Item item, int color1, int color2, int themaID, int itemID) {
        super(context);
        this.themen = item.getThemen();
        this.desc = item.getBeschreibung();
        this.title = item.getName();
        colorBackground = color1;
        colorBackgroundDark = color2;
        this.themaID = themaID;
        this.itemID = itemID;
        init(context);
    }

    private void chooseColors() {
        switch (totalItemCount)  {
            case 0:
                colorBackground = getResources().getColor(R.color.colorBackground2);
                colorBackgroundDark = getResources().getColor(R.color.colorBackground21);
                break;
            case 1:
                colorBackground = getResources().getColor(R.color.colorBackground3);
                colorBackgroundDark = getResources().getColor(R.color.colorBackground31);
                break;
            case 2:
                colorBackground = getResources().getColor(R.color.colorBackground1);
                colorBackgroundDark = getResources().getColor(R.color.colorBackground11);
                break;
            default:
                colorBackground = getResources().getColor(R.color.colorBackground1);
                break;
        }
    }

    private void init(final Context context) {
        inflate(context, R.layout.item_view, this);

        chooseColors();

        final LinearLayout container = (LinearLayout) findViewById(R.id.thee);
        container.setBackgroundColor(colorBackground);

        circleView_1 = (CircleProgressView) findViewById(R.id.circleView_1);
        circleView_1.setBarColor(Data.getBarColors());
        circleView_1.setMaxValue(Data.items.get(itemID).getThemen().size());
        circleView_1.setValue(Data.items.get(itemID).getProgress());
        circleView_1.setTextSize(0);
        circleView_1.setInnerContourSize(0);
        circleView_1.setOuterContourSize(0);
        circleView_1.setRimColor(colorBackgroundDark);

        button_themen = (TextView) findViewById(R.id.button_themen);
        button_themen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ThemaActivity.class);
                Bundle b = new Bundle();
                b.putInt("thema", themaID);
                i.putExtras(b);
                context.startActivity(i);
            }
        });

        title_1 = (TextView) findViewById(R.id.name_1);
        desc_1 = (TextView) findViewById(R.id.desc_1);

        title_1.setText(title);
        desc_1.setText(desc);

        items_1 = (TextView) findViewById(R.id.items_1);
        String text = "";
        for (int i = 0; i < themen.size(); i++) {
            text += " • "+themen.get(i).getTitel()+"\n";
        }
        items_1.setText(text);

        expandableLayout = (ExpandableLayout) findViewById(R.id.expandableLayout);
        button = (RelativeLayout) findViewById(R.id.button_1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayout.toggle();
            }
        });

        //For later
        Data.items.get(themaID).setColor(colorBackground);

        totalItemCount++;
    }
}
