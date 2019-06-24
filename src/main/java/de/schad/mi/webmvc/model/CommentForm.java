package de.schad.mi.webmvc.model;

import javax.validation.constraints.NotBlank;

/**
 * CommentForm
 */
public class CommentForm {

	@NotBlank
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