
package cz.hydradev.newsfeed.sqldelight.data.model;

import android.support.annotation.Nullable;

 final class AutoValue_ArticleSQLite extends ArticleSQLite {

  private final long id;
  private final String author;
  private final String title;
  private final String description;
  private final String url;
  private final String urlToImage;
  private final String publishedAt;
  private final String content;

  AutoValue_ArticleSQLite(
      long id,
      @Nullable String author,
      @Nullable String title,
      @Nullable String description,
      @Nullable String url,
      @Nullable String urlToImage,
      @Nullable String publishedAt,
      @Nullable String content) {
    this.id = id;
    this.author = author;
    this.title = title;
    this.description = description;
    this.url = url;
    this.urlToImage = urlToImage;
    this.publishedAt = publishedAt;
    this.content = content;
  }

  @Override
  public long id() {
    return id;
  }

  @Nullable
  @Override
  public String author() {
    return author;
  }

  @Nullable
  @Override
  public String title() {
    return title;
  }

  @Nullable
  @Override
  public String description() {
    return description;
  }

  @Nullable
  @Override
  public String url() {
    return url;
  }

  @Nullable
  @Override
  public String urlToImage() {
    return urlToImage;
  }

  @Nullable
  @Override
  public String publishedAt() {
    return publishedAt;
  }

  @Nullable
  @Override
  public String content() {
    return content;
  }

  @Override
  public String toString() {
    return "ArticleSQLite{"
        + "id=" + id + ", "
        + "author=" + author + ", "
        + "title=" + title + ", "
        + "description=" + description + ", "
        + "url=" + url + ", "
        + "urlToImage=" + urlToImage + ", "
        + "publishedAt=" + publishedAt + ", "
        + "content=" + content
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof ArticleSQLite) {
      ArticleSQLite that = (ArticleSQLite) o;
      return (this.id == that.id())
           && ((this.author == null) ? (that.author() == null) : this.author.equals(that.author()))
           && ((this.title == null) ? (that.title() == null) : this.title.equals(that.title()))
           && ((this.description == null) ? (that.description() == null) : this.description.equals(that.description()))
           && ((this.url == null) ? (that.url() == null) : this.url.equals(that.url()))
           && ((this.urlToImage == null) ? (that.urlToImage() == null) : this.urlToImage.equals(that.urlToImage()))
           && ((this.publishedAt == null) ? (that.publishedAt() == null) : this.publishedAt.equals(that.publishedAt()))
           && ((this.content == null) ? (that.content() == null) : this.content.equals(that.content()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (this.id >>> 32) ^ this.id;
    h *= 1000003;
    h ^= (author == null) ? 0 : this.author.hashCode();
    h *= 1000003;
    h ^= (title == null) ? 0 : this.title.hashCode();
    h *= 1000003;
    h ^= (description == null) ? 0 : this.description.hashCode();
    h *= 1000003;
    h ^= (url == null) ? 0 : this.url.hashCode();
    h *= 1000003;
    h ^= (urlToImage == null) ? 0 : this.urlToImage.hashCode();
    h *= 1000003;
    h ^= (publishedAt == null) ? 0 : this.publishedAt.hashCode();
    h *= 1000003;
    h ^= (content == null) ? 0 : this.content.hashCode();
    return h;
  }

}
