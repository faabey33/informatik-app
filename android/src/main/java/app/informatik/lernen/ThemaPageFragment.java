package app.informatik.lernen;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ThemaPageFragment extends Fragment {

    private TextView title;
    private TextView text;
    private ImageView image;
    private TextView button_done;

    private String title_string;
    private String text_string;
    private String image_string;

    private Drawable background;
    private int progress;
    private int itemID;
    private int themaID;

    public ThemaPageFragment() {

    }

    public ThemaPageFragment(Thema thema, int itemID, int themaID, Context context) {
        text_string = thema.getText();
        title_string = thema.getTitel();
        image_string = thema.getImg_link();
        switch (itemID) {
            case 0:
                background = context.getResources().getDrawable(R.drawable.button_blue);
                break;
            case 1:
                background = context.getResources().getDrawable(R.drawable.button_red);
                break;
            case 2:
                background = context.getResources().getDrawable(R.drawable.button_green);
                break;
            default:
                background = context.getResources().getDrawable(R.drawable.button_blue);
                break;
        }
        progress = thema.getCompleteCode();
        this.itemID = itemID;
        this.themaID = themaID;
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_thema_page, container, false);

        button_done = (TextView) v.findViewById(R.id.button_done);
        title = (TextView) v.findViewById(R.id.title);
        text = (TextView) v.findViewById(R.id.text);
        image = (ImageView) v.findViewById(R.id.image);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            text.setText(Html.fromHtml(text_string, Html.FROM_HTML_MODE_COMPACT));
        } else {
            text.setText(Html.fromHtml(text_string));
        }

        if (progress == 1) {
            button_done.setText("KANN ICH DOCH NICHT");
        } else {
            button_done.setText("HAB ICH GELERNT");
        }

        button_done.setBackgroundDrawable(background);
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progress == 1) {
                    Data.items.get(itemID).getThemen().get(themaID).setCompleteCode(-1);
                    button_done.setText("KANN ICH DOCH NICHT");
                    Data.updateProgress(itemID);
                    ThemaActivity.topicUnDone(getContext());
                } else {
                    Data.items.get(itemID).getThemen().get(themaID).setCompleteCode(1);
                    button_done.setText("HAB ICH GELERNT");
                    Data.updateProgress(itemID);
                    ThemaActivity.topicDone(getContext());
                }
            }
        });

        title.setText(title_string);
        Ion.with(image)
                .load(image_string);

        return v;
    }

}
