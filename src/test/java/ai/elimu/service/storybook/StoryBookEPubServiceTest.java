package ai.elimu.service.storybook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StoryBookEPubServiceTest {

    private StoryBookEPubService storyBookEPubService;

    @BeforeEach
    void setUp() {
        storyBookEPubService = new StoryBookEPubService();
    }

    @ParameterizedTest(name = "fileName = {0}")
    @NullAndEmptySource
    @ValueSource(strings = {
        " ",
        "  ",
        "\n",
        "\t",
        "\n\t",
        "\r",
        "fileName",
        "file name",
        "file_name",
        "file-name",
        "file.name"
    })
    void check_not_valid_table_of_contents_file_name(String fileName) {
        assertFalse(storyBookEPubService.isTableOfContentsFileHtmlLike(fileName));
    }

    @ParameterizedTest(name = "fileName = {0}")
    @ValueSource(strings = {"fileName.xhtml", "fileName.html"})
    void check_html_like_table_of_contents_file_name(String fileName) {
        assertTrue(storyBookEPubService.isTableOfContentsFileHtmlLike(fileName));
    }
}
