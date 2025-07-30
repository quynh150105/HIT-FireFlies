package com.example.hit_networking_base.service.impl;

import com.example.hit_networking_base.config.EnvironmentVariableConfig;
import com.example.hit_networking_base.constant.ErrorMessage;
import com.example.hit_networking_base.exception.BadRequestException;
import com.example.hit_networking_base.service.CheckListService;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckListServiceImpl implements CheckListService {

    private final EnvironmentVariableConfig keyBlackListConfig;
    private final ImageAnnotatorClient visionClient;

    @Override
    public boolean checkImage(MultipartFile file) {
        try {
            ByteString imgBytes = ByteString.readFrom(file.getInputStream());
            Image image = Image.newBuilder().setContent(imgBytes).build();

            List<Feature> features = List.of(
                    Feature.newBuilder().setType(Feature.Type.SAFE_SEARCH_DETECTION).build(),
                    Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build()
            );

            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addAllFeatures(features)
                    .setImage(image)
                    .build();

            AnnotateImageResponse response = visionClient.batchAnnotateImages(List.of(request)).getResponses(0);

            if (response.hasError()) {
                System.err.println("Vision API Error: " + response.getError().getMessage());
                System.out.println("====================");
                return false;
            }

            // Kiểm tra ảnh có nhạy cảm không
            SafeSearchAnnotation annotation = response.getSafeSearchAnnotation();
            if (annotation.getAdultValue() >= Likelihood.LIKELY_VALUE ||
                    annotation.getViolenceValue() >= Likelihood.LIKELY_VALUE ||
                    annotation.getRacyValue() >= Likelihood.LIKELY_VALUE) {
                System.out.println("+++++++++++");
                return false;
            }

            // Trích xuất văn bản
            String extractedText = response.getTextAnnotationsCount() > 0
                    ? response.getTextAnnotations(0).getDescription().toLowerCase()
                    : "";
            String found = checkText(extractedText);
            if(!found.isEmpty()){
                throw  new BadRequestException(ErrorMessage.Image.ERR_SENSITIVE_CONTENT + " : " + found);
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessage.Image.ERR_UPLOAD + e);
        }
    }

    @Override
    public void checkListText(Map<String, String> fields) {
        StringBuilder sensitiveWords = new StringBuilder();
        fields.forEach((key, value) -> {
            if (value != null) {
                String found = checkText(value);
                if (!found.isEmpty()) {
                    sensitiveWords.append(key).append(": ").append(found).append("; ");
                }
            }
        });
        if (!sensitiveWords.toString().isEmpty()) {
            throw new BadRequestException(ErrorMessage.Event.ERR_SENSITIVE_CONTENT + ": " + sensitiveWords.toString());
        }
    }


    public String checkText(String text) {
        String translatedBlacklist = keyBlackListConfig.getBlackList();
        List<String> blacklistWords = List.of(translatedBlacklist.toLowerCase().split(","));
        System.out.println(blacklistWords);
//        text = translateToVietnamese(text.toLowerCase());
        text = text.toLowerCase();
        StringBuilder matchedWords = new StringBuilder();
        for (String word : blacklistWords) {
            String pattern = "\\b" + Pattern.quote(word.trim()) + "\\b";
            if (Pattern.compile(pattern).matcher(text).find()) {
                matchedWords.append(word).append(", ");
            }
        }
        return matchedWords.toString().trim();

    }

//    private String translateToVietnamese(String text) {
//        Translate translate = TranslateOptions.getDefaultInstance().getService();
//        Translation translation = translate.translate(text, Translate.TranslateOption.targetLanguage("vi"));
//        return translation.getTranslatedText();
//    }

}
