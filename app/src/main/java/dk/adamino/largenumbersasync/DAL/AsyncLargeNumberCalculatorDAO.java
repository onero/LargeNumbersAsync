package dk.adamino.largenumbersasync.DAL;

import android.os.AsyncTask;
import android.util.Log;

import com.loopj.android.http.HttpGet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import dk.adamino.largenumbersasync.IAsyncCalculationCallback;
import dk.adamino.largenumbersasync.OfflineException;

/**
 * Created by Adamino.
 */
public class AsyncLargeNumberCalculatorDAO implements IAsyncLargeNumberCalculatorDAO {
    public static final String TAG = "DAL";

    public static final String REST_API_URL = "http://oleeriksen.dk/php/addm.php?";
    public static final String NUMBER1_PREFIX = "p1=";
    public static final String NUMBER_SEPARATOR = "&";
    public static final String NUMBER2_PREFIX = "p2=";

    @Override
    public void getAdditionAsync(IAsyncCalculationCallback callback, long number1, long number2) {
        RemoteCalculationTask remoteCalculationTask = new RemoteCalculationTask();
        remoteCalculationTask.setCallback(callback);
        String queryString = REST_API_URL + NUMBER1_PREFIX + number1 + NUMBER_SEPARATOR + NUMBER2_PREFIX + number2;
        remoteCalculationTask.execute(queryString);
    }

    private static class RemoteCalculationTask extends AsyncTask<String, Integer, Long> {
        public static final int ERROR_RESULT = -1;

        private IAsyncCalculationCallback mCallback;

        @Override
        protected void onPostExecute(Long result) {
            mCallback.onResult(result);
        }

        public void setCallback(IAsyncCalculationCallback callback) {
            mCallback = callback;
        }

        @Override
        protected Long doInBackground(String... strings) {
            long result = ERROR_RESULT;
            try {
                String restResult = GET(strings[0]);
                if (restResult == null) {
                    throw new OfflineException("Result was null");
                } else {
                    Log.d(TAG, restResult);
                    return jsonStringToLong(restResult);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return result;
        }

        private long jsonStringToLong(String result) throws Exception {
            long resultAsLong;
            if (result.startsWith("error")) {
                Log.d(TAG, "Error: " + result);
                throw new Exception();
            }

            if (result == null) {
                Log.d(TAG, "Error: NO RESULT");
                throw new Exception();
            }
            JSONObject calculationResult;
            try {
                calculationResult = new JSONObject(result);
                String resultAsString = calculationResult.getString("result");
                resultAsLong = Long.parseLong(resultAsString);

            } catch (JSONException e) {
                e.printStackTrace();
                throw new Exception();
            }
            return resultAsLong;
        }

        /**
         * Send default GET request to provided url
         *
         * @param url
         * @return GET response as string
         */
        private String GET(String url) {
            HttpClient httpclient = new DefaultHttpClient();
            Log.d(TAG, "DefaultHttpClient created");
            HttpGet httpget = new HttpGet(url);
            Log.d(TAG, "HttpGet from " + url + " created");
            HttpResponse response;

            try {
                response = httpclient.execute(httpget);
                Log.d(TAG, "HttpResponse executed");
                HttpEntity entity = response.getEntity();
                Log.d(TAG, "Entity loaded");

                if (entity != null) {

                    InputStream in = entity.getContent();
                    String result = convertStreamToString(in);
                    in.close();
                    return result;
                }
            } catch (ClientProtocolException e) {
                Log.d(TAG,
                        "There was a protocol based error", e);
            } catch (IOException e) {
                Log.d(TAG,
                        "There was an IO Stream related error", e);
            } catch (Exception e) {
                Log.d(TAG, "generel exception " + e.getMessage());
            }

            return null;
        }
    }

    /**
     * Convert response to String
     *
     * @param is
     * @return Response as String
     * @throws IOException
     */
    private static String convertStreamToString(InputStream is) throws IOException {
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                is.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }
}
