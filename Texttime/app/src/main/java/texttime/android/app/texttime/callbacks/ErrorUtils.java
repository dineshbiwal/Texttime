package texttime.android.app.texttime.callbacks;

import java.io.IOException;
import java.lang.annotation.Annotation;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import texttime.android.app.texttime.DataModels.RequestErrorModel;
import texttime.android.app.texttime.WebOperations.ApiClient;

/**
 * Created by TextTime Android Dev on 9/2/2016.
 */
public class ErrorUtils {
    public static RequestErrorModel parseError(Response<?> response) {
        Converter<ResponseBody, RequestErrorModel> converter =
                ApiClient.getClient()
                        .responseBodyConverter(RequestErrorModel.class, new Annotation[0]);

        RequestErrorModel error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new RequestErrorModel();
        }

        return error;
    }
}
