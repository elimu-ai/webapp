# How to Localize the Software


## Currently Supported Languages

See [INSTALL.md](INSTALL.md#supported-languages-)


## Adding Support for a New Language

See https://github.com/elimu-ai/wiki/blob/main/LOCALIZATION.md


## Adding Educational Content

The examples below use the Xhosa language as an example. If you will adding content using another language, simply replace `xho` in the URL with your language of choice.

### Adding Audio Recordings 🎶🎙️

Follow these steps to add a new audio file to the webapp's database:

1. Go to https://xho.elimu.ai/content/multimedia/audio/list

1. Press the "Add audio" button.
  
   <img width="208" src="https://user-images.githubusercontent.com/15718174/101648131-310b2a80-3a42-11eb-95f1-0eed0f183bd2.png">

1. If the audio recording is a word, the title should match the transcription:

   <img width="273" src="https://user-images.githubusercontent.com/15718174/101648783-d8885d00-3a42-11eb-840a-8c97c1d592c0.png">
   
1. However, if the audio recording is longer than a single word (e.g. a storybook paragraph), the title should be something other than the transcription:

   <img width="465" src="https://user-images.githubusercontent.com/15718174/101649083-2a30e780-3a43-11eb-943c-b6d4229f5023.png">

1. If you copied the audio file from somewhere, make sure to include the license used on the website where you downloaded the file, as well as a link to the website. If, however, you recorded the audio file yourself, _you_ can select which license to use. We recommend using [Creative Commons CC BY](https://creativecommons.org/licenses/by/4.0/):

   <img width="308" src="https://user-images.githubusercontent.com/15718174/101649572-b6430f00-3a43-11eb-8324-e4afe720de8d.png">

1. Remove silence before/after the audio, if any. This is to make sure that a child does not experience any delays while interacting with the software. We recommend using [Audacity](https://www.audacityteam.org) for editing audio recordings:

   <img width="1053" src="https://user-images.githubusercontent.com/15718174/102526312-27b63980-40a4-11eb-89ce-244351b449e7.png">

   <img width="1053" src="https://user-images.githubusercontent.com/15718174/102526322-2ab12a00-40a4-11eb-9f2a-ea85230da06a.png">


1. Then select the audio file, and press the "Add" button. If you want to provide any additional details about your contribution, you can do so in the "Comment" field:

   <img width="378" src="https://user-images.githubusercontent.com/15718174/101650579-c0b1d880-3a44-11eb-808a-4ffe292eef4b.png">

#### Adding Audio Recording via Another Page

Note that there are two other ways you can add audio recordings: 1) via the word edit page, and 2) via the storybook paragraph edit page:

##### Via the word edit page

1. At https://xho.elimu.ai/content/word/list you can find a list of words. While editing a word, you will see a warning saying "This word has no corresponding audio." if an audio recording with a matching transcription does not already exist:

   <img width="641" src="https://user-images.githubusercontent.com/15718174/101652036-69ad0300-3a46-11eb-8d77-2efe899a6c46.png">

1. Then, if you press the "Add audio" link, you will be redirected to the page for uploading an audio file, and the title and the transcription of the audio will be auto-filled:

   <img width="210" src="https://user-images.githubusercontent.com/15718174/101652281-bc86ba80-3a46-11eb-95e1-728a277c0dc2.png">

##### Via the storybook paragraph edit page

1. While editing a storybook paragrah, you'll find an "Add audio" link at the bottom:

   <img width="259" src="https://user-images.githubusercontent.com/15718174/101653094-a88f8880-3a47-11eb-9c7f-5612e3176c4b.png">

1. If you press the "Add audio" link, you will be redirected to the page for uploading an audio file, and the title and the transcription of the audio will be auto-filled to match the content of the paragraph:

   <img width="558" src="https://user-images.githubusercontent.com/15718174/101653310-dc6aae00-3a47-11eb-9296-91856d152ce9.png">

1. If you add an audio recording this way, remember to go back to the paragraph edit page after uploading the audio file, and select the corresponding audio in the drop-down:

   <img width="691" src="https://user-images.githubusercontent.com/15718174/101653932-96621a00-3a48-11eb-82e3-97598fdac82a.png">

### Adding Words

Follow these steps to add a new word to the webapp's database:

1. Go to https://xho.elimu.ai/content/word/list

1. Press the "Add word" button:
   
   <img width="209" src="https://user-images.githubusercontent.com/15718174/101672844-0a0f2180-3a5f-11eb-9401-6dcb9ad53df0.png">

1. Type the word's text:

   <img width="217" src="https://user-images.githubusercontent.com/15718174/101673126-6b36f500-3a5f-11eb-814d-19762e2d3138.png">

1. Select the word's letter-sound correspondences:

   <img width="580" src="https://user-images.githubusercontent.com/15718174/101673981-90783300-3a60-11eb-8875-cab1caafb498.png">
   
   If the letter-sound correspondence you want to use does not exist in the drop-down, press the "Add letter-sound correspondence" link. Then select the sound corresponding to the letter(s):
   
      <img width="613" src="https://user-images.githubusercontent.com/15718174/101674020-9f5ee580-3a60-11eb-898f-76ff99eaa518.png">

1. Select the _Spelling consistency_, according to how well the letters match the sounds. If you are unsure about this step, leave it unselected.

   <img width="505" src="https://user-images.githubusercontent.com/15718174/101674210-dfbe6380-3a60-11eb-8d78-fc3afccf43cf.png">

1. Select the word type (adjective, adverb, noun, etc) if it exists in the drop-down. Then press the "Add" button:

   <img width="695" src="https://user-images.githubusercontent.com/15718174/101674457-31ff8480-3a61-11eb-815e-a28e085517f1.png">

#### Adding Word via Another Page

One strategy is to add words used in a particular storybook:

1. Go to https://xho.elimu.ai/content/storybook/list and open a storybook that you want to add words for.

1. In the sidebar you'll see a word frequency list:

   <img width="337" src="https://user-images.githubusercontent.com/15718174/101676027-447abd80-3a63-11eb-8437-f391caeaabe6.png">

1. Then, press the "Add word" link if you want to create it.

1. You will then be redirected for the page for adding a word, with the word's text auto-filled:

   <img width="392" src="https://user-images.githubusercontent.com/15718174/101676218-8dcb0d00-3a63-11eb-881d-f204ccecec29.png">

1. Then, proceed as described above in ["Adding Words"](#adding-words).

Another strategy for adding words is to go to the "Words Pending" page, where you can find a list of the most used words across _all storybooks_:

1. Go to https://xho.elimu.ai/content/word/pending

   <img width="1025" src="https://user-images.githubusercontent.com/15718174/101676596-0af68200-3a64-11eb-9e2c-5087e1c8f6d9.png">

1. Here you can also choose the word to add by pressing the "Add word" link, which will redirect you to the page for adding a new word (with its text auto-filled):

   <img width="329" src="https://user-images.githubusercontent.com/15718174/101676791-527d0e00-3a64-11eb-9937-a8299f4cd7e8.png">

1. Then, proceed as described above in ["Adding Words"](#adding-words).
