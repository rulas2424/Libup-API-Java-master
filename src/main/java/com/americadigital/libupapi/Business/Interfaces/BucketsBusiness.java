package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.WsPojos.Request.AcrCloud.Buckets.AddBucketRequest;
import com.americadigital.libupapi.WsPojos.Response.AcrCloud.AcrResponse;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface BucketsBusiness {
    ResponseEntity<AcrResponse> getAllBuckets() throws IOException;

    ResponseEntity<AcrResponse> addBucket(AddBucketRequest bucketRequest) throws IOException;

    ResponseEntity<HeaderGeneric> deleteBucket(String name_bucket) throws IOException;
}
