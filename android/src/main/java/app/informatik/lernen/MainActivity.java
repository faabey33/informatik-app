package app.informatik.lernen;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.github.aakira.expandablelayout.ExpandableLayoutListener;
import com.github.aakira.expandablelayout.ExpandableLayoutListenerAdapter;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.grabner.circleprogress.CircleProgressView;

public class MainActivity extends AppCompatActivity {

    static LinearLayout container;
    ExpandableLayout expandableLayout;
    RelativeLayout button;
    TextView items_1;
    CircleProgressView circleView_1;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Data.loadColors(this);

        container = (LinearLayout) findViewById(R.id.container);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateData();
            }
        });


    }

    private void updateData() {
        Ion.with(this)
                .load(Util.getServer()+"/get_data.php")
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if (result == null) {
                            e.printStackTrace();
                            return;
                        }
                        parseData(result);
                    }
                });
    }

    private void parseData(JsonObject r) {
        JsonObject data = r.getAsJsonObject("data");
        int index = 0;

        while (data.getAsJsonObject(String.valueOf(index)) != null) {
            JsonObject entry = data.getAsJsonObject(String.valueOf(index));

            int id = Integer.valueOf(entry.get("ID").getAsString());
            String name = entry.get("name").getAsString();
            String beschreibung = entry.get("beschreibung").getAsString();
            List<Thema> themen = new ArrayList<>();

            JsonObject themenJson = entry.getAsJsonObject("themen");
            int innerIndex = 0;
            while(themenJson.getAsJsonObject(String.valueOf(innerIndex))!=null) {
                JsonObject themaInnerJson = themenJson.getAsJsonObject(String.valueOf(innerIndex));
                Thema thema = new Thema();
                thema.setTitel(themaInnerJson.get("titel").getAsString());
                thema.setText(themaInnerJson.get("text").getAsString());
                thema.setImg_link(themaInnerJson.get("bild").getAsString());
                themen.add(thema);
                innerIndex++;
            }

            Item item = new Item(id, name, beschreibung, themen);
            Data.items.add(item);

            index++;
            Log.d("asd", "asd");
        }

        onRefreshSuccess();
    }


    private static List<CustomItemView> customItemViews = new ArrayList<>();
    public static void updateProgress() {
        for (int i = 0; i < Data.items.size(); i++) {
            customItemViews.get(i).updateProgress();
        }
    }


    private void onRefreshSuccess() {
        refreshLayout.setRefreshing(false);

        for (int i = 0; i < customItemViews.size(); i++) {
            container.removeView(customItemViews.get(i));
        }

        customItemViews.clear();

        for (int i = 0; i < Data.items.size(); i++) {
            CustomItemView customItemView = new CustomItemView(this, Data.items.get(i), getResources().getColor(R.color.colorBackground3), getResources().getColor(R.color.colorBackground31), i, i);
            customItemViews.add(customItemView);
        }

        for (int i = 0; i < customItemViews.size(); i++) {
            container.addView(customItemViews.get(i));
        }


    }

    private void onRefreshFailure() {
        refreshLayout.setRefreshing(false);
    }
}
