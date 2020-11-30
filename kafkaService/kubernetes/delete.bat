kubectl delete -n default deployment kafka-cluster
kubectl delete -n default deployment zookeeper-cluster1
kubectl delete service/kafka-cluster
kubectl delete service/zookeeper-cluster1