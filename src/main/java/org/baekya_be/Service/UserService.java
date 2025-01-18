package org.baekya_be.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.baekya_be.Domain.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    public List<User> getUsers() throws Exception {
        List<User> list = new ArrayList<>();
        Firestore firestore = FirestoreClient.getFirestore();

        CollectionReference collectionRef = firestore.collection("User");
        List<QueryDocumentSnapshot> documents = collectionRef.get().get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.toObject(User.class));
        }
        return list;
    }

    public User getUser(String user_id) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collectionRef = firestore.collection("User");
        Query query = collectionRef.whereEqualTo("user_id", user_id);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
        if (!documents.isEmpty()) {
            // 첫 번째 결과를 User 객체로 반환
            return documents.get(0).toObject(User.class);
        } else {
            return null; // 해당 user_id가 없을 경우 null 반환
        }
    }

    public void addUser(User user) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collectionRef = firestore.collection("User");

        // PK로 사용할 user_id 자동 생성
        user.setUser_id(UUID.randomUUID().toString());
        collectionRef.document(user.getUser_id()).set(user);
    }

    public void deleteUser(String user_id) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collectionRef = firestore.collection("User");
        Query query = collectionRef.whereEqualTo("user_id", user_id);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            if (document.exists()) {
                collectionRef.document(document.getId()).delete();
            }
        }
    }

    public User findUserByLoginId(String loginId) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collectionRef = firestore.collection("User");
        Query query = collectionRef.whereEqualTo("loginId", loginId);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        // 디버깅 로그
        System.out.println("Found documents count: " + documents.size());
        for (DocumentSnapshot doc : documents) {
            System.out.println("Document data: " + doc.getData());
        }

        if (!documents.isEmpty() && documents.get(0).contains("password")) {
            return documents.get(0).toObject(User.class);
        } else {
            System.out.println("No valid user found for loginId: " + loginId);
            return null;
        }
    }

}