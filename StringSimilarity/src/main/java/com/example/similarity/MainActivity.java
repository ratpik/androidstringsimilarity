package com.example.similarity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.similarity.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import uk.ac.shef.wit.simmetrics.similaritymetrics.*;

public class MainActivity extends Activity {

    private Algorithm mAlgorithm;
    private String mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText searchField = (EditText) findViewById(R.id.search);

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                Toast.makeText(getApplicationContext(), "Going to check with " + mAlgorithm.getDisplayName(), Toast.LENGTH_SHORT);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                //TODO:Split multiple words if any
                mQuery = charSequence.toString();
                updateResultView(mAlgorithm, mQuery);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        Spinner spinner = (Spinner) findViewById(R.id.algorithm_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.algorithms_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

                String selectedAlgorithm = (String) adapterView.getItemAtPosition(pos);

                mAlgorithm = Algorithm.getAlgorithm(selectedAlgorithm);
                Log.d("SampleApp", "Got Algorithm " + mAlgorithm.getDisplayName());
                updateResultView(mAlgorithm, mQuery);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                String selectedAlgorithm = "";

                mAlgorithm = Algorithm.getAlgorithm(selectedAlgorithm);
                Log.d("SampleApp", "Got Algorithm " + mAlgorithm.getDisplayName());

                updateResultView(mAlgorithm, mQuery);
            }
        });


        //AbstractStringMetric metric = new Levenshtein();
        AbstractStringMetric metric = new MongeElkan();
        //AbstractStringMetric metric = new BlockDistance();
        //AbstractStringMetric metric = new ChapmanLengthDeviation();
        //AbstractStringMetric metric = new ChapmanMatchingSoundex();
        //AbstractStringMetric metric = new ChapmanMeanLength();
        //AbstractStringMetric metric = new ChapmanOrderedNameCompoundSimilarity();
        //AbstractStringMetric metric = new CosineSimilarity();
        //AbstractStringMetric metric = new DiceSimilarity();
        //AbstractStringMetric metric = new EuclideanDistance();
        //AbstractStringMetric metric = new JaccardSimilarity();
        //AbstractStringMetric metric = new MatchingCoefficient();
        //AbstractStringMetric metric = new Jaro();
        //AbstractStringMetric metric = new JaroWinkler();
        //AbstractStringMetric metric = new NeedlemanWunch();
        //AbstractStringMetric metric = new OverlapCoefficient();
        //AbstractStringMetric metric = new QGramsDistance();
        //AbstractStringMetric metric = new SmithWaterman();
        //AbstractStringMetric metric = new SmithWatermanGotoh();
        //AbstractStringMetric metric = new SmithWatermanGotohWindowedAffine();
        //AbstractStringMetric metric = new Soundex();
        //AbstractStringMetric metric = new TagLink();
        //AbstractStringMetric metric = new TagLinkToken();


    }

    private void updateResultView(Algorithm algorithm, String query) {
        AbstractStringMetric metric = Algorithm.getMetric(algorithm);

        Map.Entry<Float, String> result = findSimilarWord(metric, query);


        if (result != null) {
            TextView resultView = (TextView) findViewById(R.id.result);

            resultView.setText("Best match is " + result.getValue() + " with similarity " + result.getKey());
        }
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private Map.Entry<Float, String> findSimilarWord(AbstractStringMetric metric, String query) {

        if (query == null || query.length() < 1)
            return null;

        Map<String, Integer> dictionary = new HashMap<String, Integer>();

        TreeMap<Float, String> results = new TreeMap<Float, String>();//Natural ordeing on keys

        try {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            InputStream is = this.getAssets().open("indian_cities.txt");
            Log.d("SampleApp", "Attempting to open file");
            String line;
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                sb.append(line);
                dictionary.put(line, 0);
                //Log.d("SampleApp", "Adding to dictionary " + line);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        float similarity = 0.0f;

        for (String entry : dictionary.keySet()) {

            /*
            //If query matches exactly to word in dictionary, that is the best match
            //However here we assume that the dictionary check has already been done
            //This is only a check against different algorithms assuming input is word not in dictionary
            if(entry.equalsIgnoreCase(query))
                results.put(1.0f, entry);*/

            similarity = metric.getSimilarity(entry, query);
            if (similarity > 0.70)
                Log.d("SampleApp", "Similarity with " + entry + " is " + similarity);
            results.put(similarity, entry);
        }

        Map.Entry<Float, String> highest;
        highest = results.lastEntry();
        Log.d("SampleApp", "Highest  " + highest.getValue() + " " + highest.getKey());

        return highest;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
