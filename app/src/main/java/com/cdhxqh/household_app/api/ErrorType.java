package com.cdhxqh.household_app.api;

import android.content.Context;

import com.cdhxqh.household_app.R;


/**
 * Created by yw on 2015/5/22.
 */
public enum ErrorType {
    ErrorSuccess,
    ErrorApiForbidden,
    ErrorNoOnceAndNext,
    ErrorLoginFailure,
    ErrorLoginTimeOutFailure,
    ErrorCommentFailure,
    ErrorGetTopicListFailure,
    ErrorGetNotificationFailure,
    ErrorCreateNewFailure,
    ErrorFavNodeFailure,
    ErrorFavTopicFailure,
    ErrorGetProfileFailure;


    public static String errorMessage(Context cxt, ErrorType type) {
        boolean isNetAvailable = NetWorkHelper.isNetAvailable(cxt);
        if (!isNetAvailable)
            return cxt.getResources().getString(R.string.error_network_disconnect);

        switch (type) {
            case ErrorApiForbidden:
                return cxt.getResources().getString(R.string.error_network_exception);

            case ErrorNoOnceAndNext:
                return cxt.getResources().getString(R.string.error_obtain_once);

            case ErrorLoginFailure:
                return cxt.getResources().getString(R.string.error_login);

            case ErrorCommentFailure:
                return cxt.getResources().getString(R.string.error_reply);

            case ErrorGetNotificationFailure:
                return cxt.getString(R.string.get_info_mation_text);

            case ErrorCreateNewFailure:
                return cxt.getResources().getString(R.string.error_create_topic);

            case ErrorFavNodeFailure:
                return cxt.getResources().getString(R.string.error_fav_nodes);

            case ErrorFavTopicFailure:
                return cxt.getResources().getString(R.string.error_fav_topic);

            case ErrorGetProfileFailure:
                return cxt.getResources().getString(R.string.error_get_profile);

            case ErrorLoginTimeOutFailure:
                return cxt.getResources().getString(R.string.login_time_out);

            default:
                return cxt.getResources().getString(R.string.error_unknown);
        }

    }

    /**
     * Created by yw on 2015/5/22.
     */
    public static class SafeHandler {
        public static <E> void onFailure (HttpRequestHandler<E> handler, String error){
            try{
                handler.onFailure(error);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        public static <E> void  onSuccess(HttpRequestHandler<E> handler, String data){
            try{
                handler.onSuccess(data);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        public static <E> void  onSuccess(HttpRequestHandler<E> handler, E data, int totalPages, int currentPage){
            try{
                handler.onSuccess(data, totalPages, currentPage);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}