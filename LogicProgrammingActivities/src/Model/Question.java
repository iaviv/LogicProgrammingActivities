package Model;

public class Question {
	private String _title;
	public String getTitle() {
		return _title;
	}
	public void setTitle(String title) {
		_title = title;
	}
	
	private String _body;
	public String getBody() {
		return _body;
	}
	public void setBosy(String body) {
		_body = body;
	}
	
	private int _score;
	public int getScore() {
		return _score;
	}
	public void setScore(int score) {
		_score = score;
	}
}
