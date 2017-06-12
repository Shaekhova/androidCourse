package com.teacherhelper;

import android.content.Context;
import android.util.Log;

import com.google.api.client.http.BackOffPolicy;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.util.GenericData;
import com.kinvey.android.AsyncAppData;
import com.kinvey.android.AsyncCustomEndpoints;
import com.kinvey.android.AsyncFile;
import com.kinvey.android.AsyncLinkedData;
import com.kinvey.android.AsyncUser;
import com.kinvey.android.AsyncUserDiscovery;
import com.kinvey.android.AsyncUserGroup;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyPingCallback;
import com.kinvey.android.push.AbstractPush;
import com.kinvey.java.ClientExtension;
import com.kinvey.java.LinkedResources.LinkedGenericJson;
import com.kinvey.java.Query;
import com.kinvey.java.User;
import com.kinvey.java.auth.CredentialStore;
import com.kinvey.java.core.AbstractKinveyClientRequest;
import com.kinvey.java.core.KinveyClientCallback;
import com.kinvey.java.core.KinveyRequestInitializer;
import com.teacherhelper.model.ClassEntity;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created on 11-Jun-17.
 */

public class KniveyClient {
    public static String CLASSES_COL = "classes";
    private static String CLASSES_TEACHER_ID_COL = "teacher_id";
    private Client client;

    protected KniveyClient(Client client) {
        this.client = client;
    }

    public <T extends User> AsyncUser<T> user() {
        AsyncUser<T> user = null;
        for (int i = 0; i < 10; i++) {
            user = client.user();

            if (user == null || !user.isUserLoggedIn()) {
                return user;
            } else if (user.getUsername() != null && !user.getUsername().isEmpty()) {
                return user;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    public void createClass(ClassEntity classEntity) {
        User user = user();
        if (user.isUserLoggedIn() && user.get("isTeacher") != null) {
            classEntity.setTeacherId(user.getId());
            AsyncAppData<ClassEntity> a = client.appData(CLASSES_COL, ClassEntity.class);
            a.save(classEntity, new KinveyClientCallback<ClassEntity>() {
                @Override
                public void onSuccess(ClassEntity classEntity) {
                    Log.e("TAG", "success to save event data" + classEntity.getName());
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Log.e("TAG", "failed to save event data", throwable);
                }
            });
        }
    }

    public static Client sharedInstance() {
        return Client.sharedInstance();
    }

    public void setContext(Context context) {
        client.setContext(context);
    }

    public <T> AsyncAppData<T> appData(String collectionName, Class<T> myClass) {
        return client.appData(collectionName, myClass);
    }

    public <T extends LinkedGenericJson> AsyncLinkedData<T> linkedData(String collectionName, Class<T> myClass) {
        return client.linkedData(collectionName, myClass);
    }

    public AsyncFile file() {
        return client.file();
    }

    public void performLockDown() {
        client.performLockDown();
    }

    @Deprecated
    public AsyncCustomEndpoints customEndpoints() {
        return client.customEndpoints();
    }

    public <I, O> AsyncCustomEndpoints<I, O> customEndpoints(Class<O> myClass) {
        return client.customEndpoints(myClass);
    }

    public AsyncUserDiscovery userDiscovery() {
        return client.userDiscovery();
    }

    public AsyncUserGroup userGroup() {
        return client.userGroup();
    }

    public AbstractPush push() {
        return client.push();
    }

    public void ping(KinveyPingCallback callback) {
        client.ping(callback);
    }

    public long getSyncRate() {
        return client.getSyncRate();
    }

    public long getBatchRate() {
        return client.getBatchRate();
    }

    public int getBatchSize() {
        return client.getBatchSize();
    }

    public Context getContext() {
        return client.getContext();
    }

    public void setClientAppVersion(String appVersion) {
        client.setClientAppVersion(appVersion);
    }

    public void setClientAppVersion(int major, int minor, int revision) {
        client.setClientAppVersion(major, minor, revision);
    }

    public String getClientAppVersion() {
        return client.getClientAppVersion();
    }

    public void setCustomRequestProperties(GenericJson customheaders) {
        client.setCustomRequestProperties(customheaders);
    }

    public void setCustomRequestProperty(String key, Object value) {
        client.setCustomRequestProperty(key, value);
    }

    public void clearCustomRequestProperties() {
        client.clearCustomRequestProperties();
    }

    public GenericData getCustomRequestProperties() {
        return client.getCustomRequestProperties();
    }

    public Query query() {
        return client.query();
    }

    public Class getUserClass() {
        return client.getUserClass();
    }

    public void setUserClass(Class userClass) {
        client.setUserClass(userClass);
    }

    public boolean pingBlocking() throws IOException {
        return client.pingBlocking();
    }

    public CredentialStore getStore() {
        return client.getStore();
    }

    public void initializeRequest(AbstractKinveyClientRequest<?> httpClientRequest) throws IOException {
        client.initializeRequest(httpClientRequest);
    }

    public void enableDebugLogging() {
        client.enableDebugLogging();
    }

    public void registerExtension(ClientExtension extension) {
        client.registerExtension(extension);
    }

    public ArrayList<ClientExtension> getExtensions() {
        return client.getExtensions();
    }

    public void disableDebugLogging() {
        client.disableDebugLogging();
    }

    public boolean isUseDeltaCache() {
        return client.isUseDeltaCache();
    }

    public void setUseDeltaCache(boolean useDeltaCache) {
        client.setUseDeltaCache(useDeltaCache);
    }

    public int getRequestTimeout() {
        return client.getRequestTimeout();
    }

    public void setRequestTimeout(int requestTimeout) {
        client.setRequestTimeout(requestTimeout);
    }

    public JsonObjectParser getObjectParser() {
        return client.getObjectParser();
    }

    public JsonFactory getJsonFactory() {
        return client.getJsonFactory();
    }

    public String getRootUrl() {
        return client.getRootUrl();
    }

    public String getServicePath() {
        return client.getServicePath();
    }

    public BackOffPolicy getBackoffPolicy() {
        return client.getBackoffPolicy();
    }

    public String getBaseUrl() {
        return client.getBaseUrl();
    }

    public HttpRequestFactory getRequestFactory() {
        return client.getRequestFactory();
    }

    public KinveyRequestInitializer getKinveyRequestInitializer() {
        return client.getKinveyRequestInitializer();
    }
}
