package org.baekya_be.Service;

import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.baekya_be.DTO.SeniorAddDTO;
import org.baekya_be.DTO.SeniorExpDTO;
import org.baekya_be.Domain.Senior;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public void addSenior(SeniorAddDTO dto) throws Exception {
        Senior senior = new Senior(dto.getUser_id(), dto.getName(), dto.getJob(), dto.getCompany(), dto.getStack(),dto.getCareer(),dto.getFilter());
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collectionRef = firestore.collection("Senior");
        collectionRef.document().set(senior);
    }

    public void updateSeniorExperience(SeniorExpDTO dto) throws Exception {
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collectionRef = firestore.collection("Senior");

        Query query = collectionRef.whereEqualTo("user_id", dto.getUser_id());

        List<QueryDocumentSnapshot> documents = query.get().get().getDocuments();

        if (documents.isEmpty()) {
            throw new Exception("No document found with user_id: " + dto.getUser_id());
        }

        DocumentSnapshot document = documents.get(0);
        String documentId = document.getId();

        String currentExperience = document.getString("experience");

        String updatedExperience = currentExperience != null ? currentExperience + " " + dto.getExperience() : dto.getExperience();

        Map<String, Object> updates = new HashMap<>();
        updates.put("experience", updatedExperience);

        collectionRef.document(documentId).update(updates).get();
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

    /*public List<Senior> searchSeniors(String keyword) throws Exception {
        List<Senior> list = new ArrayList<>();
        Firestore firestore = FirestoreClient.getFirestore();

        CollectionReference collectionRef = firestore.collection("Senior");
        List<QueryDocumentSnapshot> documents = collectionRef.get().get().getDocuments();

        List<String> keywords = Arrays.asList(keyword.split("[,\\s]+"));

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
                        String substring = "";
                        if (word != null && word.length() > 1) {
                            substring = word.substring(0, word.length() - 1); // 마지막 문자 제외
                        }
                        if (word.equals(keyword) || substring.equals(keyword)) {
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
    }*/
    public List<Senior> searchSeniors(String keyword) throws Exception {
        List<Senior> list = new ArrayList<>();
        Firestore firestore = FirestoreClient.getFirestore();

        CollectionReference collectionRef = firestore.collection("Senior");
        List<QueryDocumentSnapshot> documents = collectionRef.get().get().getDocuments();

        List<String> keywords = Arrays.asList(keyword.split("[,\\s]+"));

        for (QueryDocumentSnapshot document : documents) {
            boolean matchFound = false;

            List<String> stack = (List<String>) document.get("stack");
            String experience = document.getString("experience");
            String job = document.getString("job");
            String name = document.getString("name");
            String company = document.getString("company");

            for (String keywordPart : keywords) {
                if (!matchFound && stack != null) {
                    for (String item : stack) {
                        if (item != null && item.equals(keywordPart)) {
                            matchFound = true;
                            break;
                        }
                    }
                }

                if (!matchFound && experience != null) {
                    String[] words = experience.replace(".", "").split("\\s+");
                    for (String word : words) {
                        String substring = "";
                        String substring2 = "";
                        if (word != null && word.length() > 1) {
                            substring = word.substring(0, word.length() - 1);
                        }
                        if (word != null && word.length() > 2) {
                            substring2 = word.substring(0, word.length() - 2);
                        }
                        if (word.equals(keywordPart) || substring.equals(keywordPart) || substring2.equals(keywordPart)) {
                            matchFound = true;
                            break;
                        }
                    }
                }

                if (!matchFound && job != null) {
                    String[] jobParts = job.split("/");
                    for (String jobPart : jobParts) {
                        if (jobPart.equals(keywordPart)) {
                            matchFound = true;
                            break;
                        }
                    }
                }

                if (!matchFound &&
                        (Objects.equals(job, keywordPart) ||
                                Objects.equals(name, keywordPart) ||
                                Objects.equals(company, keywordPart))) {
                    matchFound = true;
                }

                if (matchFound) {
                    break;
                }
            }

            if (matchFound) {
                list.add(document.toObject(Senior.class));
            }
        }
        return list;
    }
}
