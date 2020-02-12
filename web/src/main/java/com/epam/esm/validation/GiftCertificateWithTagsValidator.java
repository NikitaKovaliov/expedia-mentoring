package com.epam.esm.validation;

import com.epam.esm.dto.GiftCertificateWithTags;
import com.epam.esm.model.Tag;
import java.math.BigDecimal;
import java.util.List;

public class GiftCertificateWithTagsValidator {

  private static final int MAX_CERTIFICATE_NAME_LENGTH = 50;
  private static final int MAX_CERTIFICATE_DESCRIPTION_LENGTH = 200;
  private static final double MIN_CERTIFICATE_PRICE = 0;
  private static final int MIN_CERTIFICATE_DURATION = 1;

  private GiftCertificateWithTagsValidator() {

  }

  public static boolean isValidGiftCertificateValuesForCreate(GiftCertificateWithTags certificate) {
    return certificate.getName() != null && isValidGiftCertificateName(certificate.getName())
        && certificate.getDescription() != null && isValidGiftCertificateDescription(certificate.getDescription())
        && certificate.getPrice() != null && isValidCertificatePrice(certificate.getPrice())
        && certificate.getDuration() != null && isValidCertificateDuration(certificate.getDuration())
        && isValidAllCertificatesTags(certificate);
  }

  public static boolean isValidGiftCertificateValuesForUpdate(GiftCertificateWithTags certificate) {
    return (certificate.getName() == null || isValidGiftCertificateName(certificate.getName())
        && (certificate.getDescription() == null || isValidGiftCertificateDescription(certificate.getDescription()))
        && (certificate.getPrice() == null || isValidCertificatePrice(certificate.getPrice()))
        && (certificate.getDuration() == null || isValidCertificateDuration(certificate.getDuration()))
        && isValidAllCertificatesTags(certificate));
  }

  public static boolean hasFieldsForUpdate(GiftCertificateWithTags certificate) {
    return certificate.getName() != null || certificate.getDescription() != null ||
        certificate.getPrice() != null || certificate.getDuration() != null ||
        (certificate.getTags() != null && !certificate.getTags().isEmpty()) ||
        (certificate.getTagsForDeletion() != null && !certificate.getTagsForDeletion().isEmpty());
  }

  private static boolean isValidAllCertificatesTags(GiftCertificateWithTags certificate) {
    return isValidTagList(certificate.getTags()) && isValidTagList(certificate.getTagsForDeletion());
  }

  private static boolean isValidGiftCertificateName(String name) {
    return !name.isEmpty() && name.length() <= MAX_CERTIFICATE_NAME_LENGTH;
  }

  private static boolean isValidGiftCertificateDescription(String description) {
    return !description.isEmpty() && description.length() <= MAX_CERTIFICATE_DESCRIPTION_LENGTH;
  }

  private static boolean isValidCertificatePrice(BigDecimal price) {
    return price.doubleValue() >= MIN_CERTIFICATE_PRICE;
  }

  private static boolean isValidCertificateDuration(Integer duration) {
    return duration >= MIN_CERTIFICATE_DURATION;
  }

  private static boolean isValidTagList(List<Tag> tags) {
    return tags == null || tags.isEmpty() || tags.stream().allMatch(TagValidator::isValidTag);
  }
}