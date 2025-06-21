<content:title>
    ${researchExperiment}
</content:title>

<content:section cssId="experimentPage">
    <div class="row">
        <h5>Experiment: <code><content:gettitle /></code></h5>
        <blockquote class="deep-purple lighten-5" style="padding: 1em;">
            <span class="grey-text">Theory:</span> ${researchExperiment.theory}
        </blockquote>
    </div>
    
    <div class="row">
        <table class="bordered highlight">
            <thead>
                <th>Data Type</th>
                <th><code>CONTROL</code></th>
                <th><code>TREATMENT</code></th>
            </thead>
            <tbody>
                <tr>
                    <td>🎼 Letter-sound assessment events</td>
                    <td>
                        <div class="progress">
                            <div class="determinate" style="width: ${fn:length(letterSoundAssessmentEvents_CONTROL) * 100 / (fn:length(letterSoundAssessmentEvents_CONTROL) + fn:length(letterSoundAssessmentEvents_TREATMENT))}%"></div>
                        </div>
                        ${fn:length(letterSoundAssessmentEvents_CONTROL)}
                    </td>
                    <td>
                        <span class="progress">
                            <div class="determinate" style="width: ${fn:length(letterSoundAssessmentEvents_TREATMENT) * 100 / (fn:length(letterSoundAssessmentEvents_CONTROL) + fn:length(letterSoundAssessmentEvents_TREATMENT))}%"></div>
                        </span>
                        ${fn:length(letterSoundAssessmentEvents_TREATMENT)}
                    </td>
                </tr>
                <tr>
                    <td>🎼 Letter-sound learning events</td>
                    <td>
                        <div class="progress">
                            <div class="determinate" style="width: ${fn:length(letterSoundLearningEvents_CONTROL) * 100 / (fn:length(letterSoundLearningEvents_CONTROL) + fn:length(letterSoundLearningEvents_TREATMENT))}%"></div>
                        </div>
                        ${fn:length(letterSoundLearningEvents_CONTROL)}
                    </td>
                    <td>
                        <span class="progress">
                            <div class="determinate" style="width: ${fn:length(letterSoundLearningEvents_TREATMENT) * 100 / (fn:length(letterSoundLearningEvents_CONTROL) + fn:length(letterSoundLearningEvents_TREATMENT))}%"></div>
                        </span>
                        ${fn:length(letterSoundLearningEvents_TREATMENT)}
                    </td>
                </tr>

                <tr>
                    <td>🔤 Word assessment events</td>
                    <td>
                        <div class="progress">
                            <div class="determinate" style="width: ${fn:length(wordAssessmentEvents_CONTROL) * 100 / (fn:length(wordAssessmentEvents_CONTROL) + fn:length(wordAssessmentEvents_TREATMENT))}%"></div>
                        </div>
                        ${fn:length(wordAssessmentEvents_CONTROL)}
                    </td>
                    <td>
                        <span class="progress">
                            <div class="determinate" style="width: ${fn:length(wordAssessmentEvents_TREATMENT) * 100 / (fn:length(wordAssessmentEvents_CONTROL) + fn:length(wordAssessmentEvents_TREATMENT))}%"></div>
                        </span>
                        ${fn:length(wordAssessmentEvents_TREATMENT)}
                    </td>
                </tr>
                <tr>
                    <td>🔤 Word learning events</td>
                    <td>
                        <div class="progress">
                            <div class="determinate" style="width: ${fn:length(wordLearningEvents_CONTROL) * 100 / (fn:length(wordLearningEvents_CONTROL) + fn:length(wordLearningEvents_TREATMENT))}%"></div>
                        </div>
                        ${fn:length(wordLearningEvents_CONTROL)}
                    </td>
                    <td>
                        <span class="progress">
                            <div class="determinate" style="width: ${fn:length(wordLearningEvents_TREATMENT) * 100 / (fn:length(wordLearningEvents_CONTROL) + fn:length(wordLearningEvents_TREATMENT))}%"></div>
                        </span>
                        ${fn:length(wordLearningEvents_TREATMENT)}
                    </td>
                </tr>

                <tr>
                    <td>🔢 Number assessment events</td>
                    <td>
                        <div class="progress">
                            <div class="determinate" style="width: ${fn:length(numberAssessmentEvents_CONTROL) * 100 / (fn:length(numberAssessmentEvents_CONTROL) + fn:length(numberAssessmentEvents_TREATMENT))}%"></div>
                        </div>
                        ${fn:length(numberAssessmentEvents_CONTROL)}
                    </td>
                    <td>
                        <span class="progress">
                            <div class="determinate" style="width: ${fn:length(numberAssessmentEvents_TREATMENT) * 100 / (fn:length(numberAssessmentEvents_CONTROL) + fn:length(numberAssessmentEvents_TREATMENT))}%"></div>
                        </span>
                        ${fn:length(numberAssessmentEvents_TREATMENT)}
                    </td>
                </tr>
                <tr>
                    <td>🔢 Number learning events</td>
                    <td>
                        <div class="progress">
                            <div class="determinate" style="width: ${fn:length(numberLearningEvents_CONTROL) * 100 / (fn:length(numberLearningEvents_CONTROL) + fn:length(numberLearningEvents_TREATMENT))}%"></div>
                        </div>
                        ${fn:length(numberLearningEvents_CONTROL)}
                    </td>
                    <td>
                        <span class="progress">
                            <div class="determinate" style="width: ${fn:length(numberLearningEvents_TREATMENT) * 100 / (fn:length(numberLearningEvents_CONTROL) + fn:length(numberLearningEvents_TREATMENT))}%"></div>
                        </span>
                        ${fn:length(numberLearningEvents_TREATMENT)}
                    </td>
                </tr>

                <tr>
                    <td>📚 Storybook learning events</td>
                    <td>
                        <div class="progress">
                            <div class="determinate" style="width: ${fn:length(storyBookLearningEvents_CONTROL) * 100 / (fn:length(storyBookLearningEvents_CONTROL) + fn:length(storyBookLearningEvents_TREATMENT))}%"></div>
                        </div>
                        ${fn:length(storyBookLearningEvents_CONTROL)}
                    </td>
                    <td>
                        <span class="progress">
                            <div class="determinate" style="width: ${fn:length(storyBookLearningEvents_TREATMENT) * 100 / (fn:length(storyBookLearningEvents_CONTROL) + fn:length(storyBookLearningEvents_TREATMENT))}%"></div>
                        </span>
                        ${fn:length(storyBookLearningEvents_TREATMENT)}
                    </td>
                </tr>

                <tr>
                    <td>🎬 Video learning events</td>
                    <td>
                        <div class="progress">
                            <div class="determinate" style="width: ${fn:length(videoLearningEvents_CONTROL) * 100 / (fn:length(videoLearningEvents_CONTROL) + fn:length(videoLearningEvents_TREATMENT))}%"></div>
                        </div>
                        ${fn:length(videoLearningEvents_CONTROL)}
                    </td>
                    <td>
                        <span class="progress">
                            <div class="determinate" style="width: ${fn:length(videoLearningEvents_TREATMENT) * 100 / (fn:length(videoLearningEvents_CONTROL) + fn:length(videoLearningEvents_TREATMENT))}%"></div>
                        </span>
                        ${fn:length(videoLearningEvents_TREATMENT)}
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</content:section>
