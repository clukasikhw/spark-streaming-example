#!/usr/bin/env bash

# Run this script as kinit'd real end-user. E.g.
# kinit -kt /etc/security/keytabs/smokeuser.headless.keytab ambari-qa-CRAIG_A@EXAMPLE.COM

hdfs dfs -mkdir -p /tmp/stream-test/inbound

hdfs dfs -mkdir -p /tmp/stream-test/outbound

echo "Put files and stuff in the inbound dir"

spark-submit \
  --master yarn-cluster \
  --num-executors 2 \
  --class com.hortonworks.examples.StreamingExample \
  ../lib/spark-stream-test-0.0.1-SNAPSHOT.jar \
  10 \
  /tmp/stream-test/inbound \
  /tmp/stream-test/outbound