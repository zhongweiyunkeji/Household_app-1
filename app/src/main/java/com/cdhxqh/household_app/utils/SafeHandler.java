package com.cdhxqh.household_app.utils;


import com.cdhxqh.household_app.api.HttpRequestHandler;

/**
 * Created by yw on 2015/5/22.
 */
public class SafeHandler {
    public static <E> void onFailure (HttpRequestHandler<E> handler, String error){
        try{
            handler.onFailure(error);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static <E> void  onSuccess(HttpRequestHandler<E> handler, E data){
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
