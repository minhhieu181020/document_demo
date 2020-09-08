package com.lpb.lifecardsdk.util;


public class GsonUtils {

//    private static Gson instance;
//
//    public static synchronized Gson getInstance() {
//        if (instance == null) {
//            GsonBuilder gsonBuilder = new GsonBuilder();
//            gsonBuilder.registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
//                @Override
//                public JsonElement serialize(Double originalValue, Type typeOf,
//                                             JsonSerializationContext context) {
//                    BigDecimal bigValue = BigDecimal.valueOf(originalValue);
//
//                    return new JsonPrimitive(bigValue.toPlainString());
//                }
//            });
//
//            gsonBuilder.excludeFieldsWithoutExposeAnnotation();
//            instance = gsonBuilder.create();
//        }
//
//        return instance;
//    }
//
//    public static String object2String(Object obj) {
//
//        return getInstance().toJson(obj);
//    }
//
//    public static <T> T string2Object(String json, Class<T> clzz) {
//        return getInstance().fromJson(json, clzz);
//    }
//
//    public static <T> T string2Object(String json, Type typeOfT) {
//        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
//        return gson.fromJson(json, typeOfT);
//    }
//
//    public static <T> List<T> string2ListObject(String json, Class<T[]> clazz) {
//        T[] t = getInstance().fromJson(json, clazz);
//        return Arrays.asList(t);
//    }
//
//    public static <T> T string2Object(String json, TypeToken<T> tTypeToken) {
//        return getInstance().fromJson(json, tTypeToken.getType());
//    }
//
//    public static JSONObject object2JSONObject(Object obj) {
//        try {
//            return new JSONObject(object2String(obj));
//        } catch (JSONException e) {
//            return null;
//        }
//    }
//
//    public static <T extends BaseBodyResponse> BaseResponse<T> jsonObject2Object(JSONObject jsonObject, Class<T> clzz) {
//
//        Type type = TypeToken.getParameterized(BaseResponse.class, clzz).getType();
//
//        return string2Object(jsonObject.toString(), type);
//    }
//
//    public static <T> List<T> jsonObject2ListObject(JSONObject jsonObject, Class<T[]> clzz) {
//        return string2ListObject(jsonObject.toString(), clzz);
//    }
}
