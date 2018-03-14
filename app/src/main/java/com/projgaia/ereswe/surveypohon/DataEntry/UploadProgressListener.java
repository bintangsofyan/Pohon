package com.projgaia.ereswe.surveypohon.DataEntry;

/**
 * Created by ERESWE on 23/09/2017.
 */
public interface UploadProgressListener {
    /**
     * This method updated how much data size uploaded to server
     *
     * @param num
     */
    void transferred(long num);
}