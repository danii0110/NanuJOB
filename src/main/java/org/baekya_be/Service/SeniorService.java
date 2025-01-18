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

    public List<Senior> searchSeniors(String keyword) throws Exception {
        List<Senior> list = new ArrayList<>();
        Firestore firestore = FirestoreClient.getFirestore();

        CollectionReference collectionRef = firestore.collection("Senior");
        List<QueryDocumentSnapshot> documents = collectionRef.get().get().getDocuments();


        for (QueryDocumentSnapshot document : documents) {
            boolean flag = false;

            List<String> stack = (List<String>) document.get("stack");
            String experience = document.getString("experience");

            if (stack != null) {
                for (String item : stack) {
                    if (item != null && item.equals(keyword)) {
                        flag = true;
                        break;
                    }
                }
            }

            if (!flag){
                if (experience != null) {
                    String[] words = experience.replace(".", "").split("\\s+");
                    for (String word : words) {
                        if (word.equals(keyword)) {
                            flag = true;
                            break;
                        }
                    }
                }
            }

            if (Objects.requireNonNull(document.getString("job")).equals(keyword) ||
            Objects.requireNonNull(document.getString("name")).equals(keyword) ||
            Objects.requireNonNull(document.getString("company")).equals(keyword) ||
            flag) {
                list.add(document.toObject(Senior.class));
            }
        }
        return list;
    }
}
