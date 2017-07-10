# News Reader
Reading the news (whether from traditional media or social networks) is a common use of mobile devices—the image of the subway full of newspaper-readers has been replaced with the subway full of phone-readers. This repo contains code of a Android application that allows the user to browse the day's news.

## User Stories

The user stories for the News Reader app are:

+ As a user, I want to view a list of recent news stories so I can know what is going on in the world.
+ As a user, I want to search for recent news about a particular topic.
+ As a user, I want to easily preview stories so I can determine if they are relevant to my interests.
+ As a user, I want to be able to read a full news article so I can get the whole story.

## Appearance and Interaction

The application is made up of five (5) different "views":

+ A scrollable "recent list" of new articles that have recently been published. Each item in the list includes the article headline as well as the date when it was published.

+ A "preview" of a single news article, which includes the following elements:

  + The headline of the article.
  + A snippet of the article, a summary of the content.
  + An image that accompanies the article. 

+ A "full article" view, showing the full text of the article. This is a Webview that's showing the original article.

+ A view to "search" for articles. The user is able to enter a search query as well as a range of dates to search (default ranging from the current date to one week in the past) by specifying the begin and end dates for the search using pop-up dialogs. 

+ A "search results" view showing a list of articles matching the search query.
