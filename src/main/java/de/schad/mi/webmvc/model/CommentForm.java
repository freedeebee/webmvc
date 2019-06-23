package de.schad.mi.webmvc.model;

/**
 * CommentForm
 */
public class CommentForm {

	private String comment;

	public CommentForm() {}

	public CommentForm(String comment) {
		this.comment = comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return this.comment;
	}
}