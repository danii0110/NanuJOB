package org.baekya_be.Service;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.baekya_be.Domain.Senior;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SeniorService {
    public List<Senior> getSeniors() throws Exception {
        List<Senior> list = new ArrayList<>();
        Firestore firestore = FirestoreClient.getFirestore();

        CollectionReference collectionRef = firestore.collection("Senior");
        List<QueryDocumentSnapshot> documents = collectionRef.get().get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.toObject(Senior.class));
        }
        return list;
    }

    public List<Senior> seniorsFilter(String filter) throws Exception {
        List<Senior> list = new ArrayList<>();
        Firestore firestore = FirestoreClient.getFirestore();

        CollectionReference collectionRef = firestore.collection("Senior");
        List<QueryDocumentSnapshot> documents = collectionRef.get().get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            if (Objects.requireNonNull(document.getString("filter")).equals(filter)) {
                list.add(document.toObject(Senior.class));
            }
        }
        return list;
    }
}
