package edu.nju.ticket.common;

public enum OrderState {
	distributed(1),
	undistributed(2),
	canceled(3),
	lackseat(4);
    private int state;  
    // ���췽��  
    private OrderState(int state) {  
    	this.state=state;
    }  
    //���Ƿ���  
    @Override  
    public String toString() {  
        return String.valueOf(this.state);  
    }  
    
    public int toInt()
    {
    	return this.state;
    }
}
