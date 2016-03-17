package kent.kentapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.*;

public class Directory extends Activity {

    HashMap<String, List<String>> optionsHashMap;
    List<String> optionsHashMapKeys;
    ExpandableListView expandableListView;
    DirectoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);

        expandableListView = (ExpandableListView) findViewById(R.id.directory_list);
        optionsHashMap = DataProvider.getDataHashMap();
        optionsHashMapKeys = new ArrayList<>(optionsHashMap.keySet());

        adapter = new DirectoryAdapter(this, optionsHashMap, optionsHashMapKeys);
        expandableListView.setAdapter(adapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(Directory.this,
                        optionsHashMapKeys.get(groupPosition)
                                + " expanded", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(Directory.this, optionsHashMapKeys.get(groupPosition) + " collapsed", Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View clickedView, int groupPosition, int childPosition, long id) {
                Toast.makeText(Directory.this, "Selected " + optionsHashMap.get(optionsHashMapKeys.get(groupPosition)).get(childPosition)
                        + " from " + optionsHashMapKeys.get(groupPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        final ImageButton newsBtn = (ImageButton) findViewById(R.id.newsBtn);
        newsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Directory.this, News.class);
                startActivity(intent);
                //finish();
            }
        });

        final ImageButton calendarBtn = (ImageButton) findViewById(R.id.calendarBtn);
        calendarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Directory.this, Calendar.class);
                startActivity(intent);
                //finish();
            }
        });

        final ImageButton socialBtn = (ImageButton) findViewById(R.id.socialBtn);
        socialBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Directory.this, Social.class);
                startActivity(intent);
                //finish();
            }
        });

        final ImageButton mapBtn = (ImageButton) findViewById(R.id.mapsBtn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Directory.this, CampusMap.class);
                startActivity(intent);
                //finish();
            }
        });

        final ImageButton moreBtn = (ImageButton) findViewById(R.id.moreBtn);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Directory.this, More.class);
                startActivity(intent);
                //finish();
            }
        });
        
    }
}

class DataArrays {
    static String[] medicalCentre = {"University Medical Centre" + "Giles Lane\n" +
            "Canterbury\n" + "Kent\n" + "CT2 7PB\n" + "Telephone: 01227 469333\n" + "http://www.umckent.co.uk/\n"};
    static String[] finances = {"Finance Department" + "The Registry\n" + "University of Kent\n" + "Canterbury\n" +
            "Kent\n" + "Fax: +44(0)1227 827024\n"};
    static String[] security = {"Canterbury Campus" + "University of Kent,\n" +
            "Canterbury,\n" + "Kent\n" + "CT2 7NZ\n ", "Campus Watch: +44 (0)1227 868253", "Campus Watch: 07772 226188",
            "Campus Watch: 07772 226188"};
}

class DataProvider {

    public static HashMap<String, List<String>> getDataHashMap() {
        HashMap<String, List<String>> optionsHashMap = new HashMap<String, List<String>>();

        List<String> medicalCentreList = new ArrayList<String>();
        List<String> securityList = new ArrayList<String>();
        List<String> financeList = new ArrayList<String>();

        for (int i = 0; i < DataArrays.medicalCentre.length; i++) {
            medicalCentreList.add(DataArrays.medicalCentre[i]);
        }

        for (int i = 0; i < DataArrays.security.length; i++) {
            securityList.add(DataArrays.security[i]);
        }

        for (int i = 0; i < DataArrays.finances.length; i++) {
            financeList.add(DataArrays.finances[i]);
        }

        optionsHashMap.put("Medical Centre", medicalCentreList);
        optionsHashMap.put("Campus Security", securityList);
        optionsHashMap.put("Finance", financeList);

        return optionsHashMap;

    }
}

class DirectoryAdapter extends BaseExpandableListAdapter {


    private Context context;
    private HashMap<String, List<String>> optionsHashMap;
    private List<String> directoryOptions;

    public DirectoryAdapter(Context context, HashMap<String,
            List<String>> hashMap, List<String> list) {
        optionsHashMap = hashMap;
        this.context = context;
        this.optionsHashMap = hashMap;
        this.directoryOptions = list;
    }

    @Override
    public int getGroupCount() {
        return optionsHashMap.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return optionsHashMap.get(directoryOptions.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return directoryOptions.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return optionsHashMap.get(directoryOptions.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String groupTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_directory, parent, false);
        }
        TextView parentTextView = (TextView) convertView.findViewById(R.id.textViewParent);
        parentTextView.setText(groupTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition,
                             int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        Log.i("test", "parent view: " + parent.getTag());

        String childTitle = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_directory_child, parent, false);
        }
        TextView childTextView = (TextView) convertView.findViewById(R.id.textViewChild);
        childTextView.setText(childTitle);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}