package com.solu4b.nrafalow.popularmoviesstage1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FetchMovieTask movieTask = new FetchMovieTask();

        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String mMovieStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            movieTask.execute(mMovieStr);
        }

        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, Movie> {

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected Movie doInBackground(String... strings) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String movieJsonStr = null;
            String baseUrl = "http://api.themoviedb.org/3/movie/";
            String apiKey = "";

            try {
                final String FORECAST_BASE_URL = baseUrl + strings[0];
                final String API_KEY_PARAM = "api_key";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(API_KEY_PARAM, apiKey)
                        .build();

                URL url = new URL(builtUri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                movieJsonStr = buffer.toString();

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDetailFromJson(movieJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Movie result) {
            String baseUrl = "http://image.tmdb.org/t/p/w342/";

            if (result != null) {
                TextView originalTitle = (TextView) getView().findViewById(R.id.original_title);
                TextView getReleaseDate = (TextView) getView().findViewById(R.id.release_date);
                ImageView posterThumb = (ImageView) getView().findViewById(R.id.poster_thumb);
                TextView voteAverage = (TextView) getView().findViewById(R.id.vote_average);
                TextView overview = (TextView) getView().findViewById(R.id.overview);

                originalTitle.setText(result.getOriginalTitle());
                getReleaseDate.setText(result.getReleaseDate());

                String path = baseUrl + result.getPosterPath() ;
                Picasso.with(getActivity()).load(path).into(posterThumb);

                voteAverage.setText(result.getVoteAverage());
                overview.setText(result.getOverview());
            }
        }

        private Movie getMovieDetailFromJson(String movieJsonStr)
                throws JSONException {

            String title;
            String posterPath;
            String overview;
            String voteAverage;
            String releaseDate;

            final String OWN_TITLE = "original_title";
            final String OWN_POSTER_PATH = "poster_path";
            final String OWN_OVERVIEW = "overview";
            final String OWN_VOTE_AVERAGE = "vote_average";
            final String OWN_RELEASE_DATE = "release_date";


            JSONObject movieJson = new JSONObject(movieJsonStr);
            title = movieJson.getString(OWN_TITLE);
            posterPath = movieJson.getString(OWN_POSTER_PATH);
            overview = movieJson.getString(OWN_OVERVIEW);
            voteAverage = movieJson.getString(OWN_VOTE_AVERAGE);
            releaseDate = movieJson.getString(OWN_RELEASE_DATE);

            Movie detail = new Movie();
            detail.setOriginalTitle(title);
            detail.setPosterPath(posterPath);
            detail.setOverview(overview);
            detail.setVoteAverage(voteAverage);
            detail.setReleaseDate(releaseDate);

            return detail;
        }
    }
}