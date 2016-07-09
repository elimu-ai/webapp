package org.literacyapp.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.literacyapp.model.enums.Locale;

@Entity
public class Student extends BaseEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    private Locale locale;
    
    @NotEmpty
    @OneToMany
    private Set<Device> devices;
    
    // LiteracySkill levels (e.g. 0.005 = 0.5%)
    private Double levelVocabulary;
    private Double levelSoundIdentification;
    private Double levelGraphemeKnowledge;
    private Double levelFamiliarWordReading;
    private Double levelInventedWordDecoding;
    private Double levelOralPassageReading;
    private Double levelReadingComprehension;
    private Double levelListeningComprehension;
    private Double levelSentenceWriting;
    
    // NumeracySkill levels (e.g. 0.005 = 0.5%)
    private Double levelOralCounting;
    private Double levelOneToOneCorrespondence;
    private Double levelNumberIdentification;
    private Double levelQuantityDiscrimination;
    private Double levelMissingNumber;
    private Double levelAddition;
    private Double levelSubtraction;
    private Double levelMultiplication;
    private Double levelWordProblems;
    private Double levelShapeIdentification;
    private Double levelShapeNaming;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    public Double getLevelVocabulary() {
        return levelVocabulary;
    }

    public void setLevelVocabulary(Double levelVocabulary) {
        this.levelVocabulary = levelVocabulary;
    }

    public Double getLevelSoundIdentification() {
        return levelSoundIdentification;
    }

    public void setLevelSoundIdentification(Double levelSoundIdentification) {
        this.levelSoundIdentification = levelSoundIdentification;
    }

    public Double getLevelGraphemeKnowledge() {
        return levelGraphemeKnowledge;
    }

    public void setLevelGraphemeKnowledge(Double levelGraphemeKnowledge) {
        this.levelGraphemeKnowledge = levelGraphemeKnowledge;
    }

    public Double getLevelFamiliarWordReading() {
        return levelFamiliarWordReading;
    }

    public void setLevelFamiliarWordReading(Double levelFamiliarWordReading) {
        this.levelFamiliarWordReading = levelFamiliarWordReading;
    }

    public Double getLevelInventedWordDecoding() {
        return levelInventedWordDecoding;
    }

    public void setLevelInventedWordDecoding(Double levelInventedWordDecoding) {
        this.levelInventedWordDecoding = levelInventedWordDecoding;
    }

    public Double getLevelOralPassageReading() {
        return levelOralPassageReading;
    }

    public void setLevelOralPassageReading(Double levelOralPassageReading) {
        this.levelOralPassageReading = levelOralPassageReading;
    }

    public Double getLevelReadingComprehension() {
        return levelReadingComprehension;
    }

    public void setLevelReadingComprehension(Double levelReadingComprehension) {
        this.levelReadingComprehension = levelReadingComprehension;
    }

    public Double getLevelListeningComprehension() {
        return levelListeningComprehension;
    }

    public void setLevelListeningComprehension(Double levelListeningComprehension) {
        this.levelListeningComprehension = levelListeningComprehension;
    }

    public Double getLevelSentenceWriting() {
        return levelSentenceWriting;
    }

    public void setLevelSentenceWriting(Double levelSentenceWriting) {
        this.levelSentenceWriting = levelSentenceWriting;
    }

    public Double getLevelOralCounting() {
        return levelOralCounting;
    }

    public void setLevelOralCounting(Double levelOralCounting) {
        this.levelOralCounting = levelOralCounting;
    }

    public Double getLevelOneToOneCorrespondence() {
        return levelOneToOneCorrespondence;
    }

    public void setLevelOneToOneCorrespondence(Double levelOneToOneCorrespondence) {
        this.levelOneToOneCorrespondence = levelOneToOneCorrespondence;
    }

    public Double getLevelNumberIdentification() {
        return levelNumberIdentification;
    }

    public void setLevelNumberIdentification(Double levelNumberIdentification) {
        this.levelNumberIdentification = levelNumberIdentification;
    }

    public Double getLevelQuantityDiscrimination() {
        return levelQuantityDiscrimination;
    }

    public void setLevelQuantityDiscrimination(Double levelQuantityDiscrimination) {
        this.levelQuantityDiscrimination = levelQuantityDiscrimination;
    }

    public Double getLevelMissingNumber() {
        return levelMissingNumber;
    }

    public void setLevelMissingNumber(Double levelMissingNumber) {
        this.levelMissingNumber = levelMissingNumber;
    }

    public Double getLevelAddition() {
        return levelAddition;
    }

    public void setLevelAddition(Double levelAddition) {
        this.levelAddition = levelAddition;
    }

    public Double getLevelSubtraction() {
        return levelSubtraction;
    }

    public void setLevelSubtraction(Double levelSubtraction) {
        this.levelSubtraction = levelSubtraction;
    }

    public Double getLevelMultiplication() {
        return levelMultiplication;
    }

    public void setLevelMultiplication(Double levelMultiplication) {
        this.levelMultiplication = levelMultiplication;
    }

    public Double getLevelWordProblems() {
        return levelWordProblems;
    }

    public void setLevelWordProblems(Double levelWordProblems) {
        this.levelWordProblems = levelWordProblems;
    }

    public Double getLevelShapeIdentification() {
        return levelShapeIdentification;
    }

    public void setLevelShapeIdentification(Double levelShapeIdentification) {
        this.levelShapeIdentification = levelShapeIdentification;
    }

    public Double getLevelShapeNaming() {
        return levelShapeNaming;
    }

    public void setLevelShapeNaming(Double levelShapeNaming) {
        this.levelShapeNaming = levelShapeNaming;
    }
}
