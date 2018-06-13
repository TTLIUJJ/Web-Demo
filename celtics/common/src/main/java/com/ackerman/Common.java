package com.ackerman;

/**
 * @Author: Ackerman
 * @Description:
 * @Date: Created in 上午9:37 18-6-7
 */
public interface Common {
    public String say(String s);

    public boolean isLocalSession(String key, String val);

    public boolean verifyToken(String token);

    public String getTicketKey(String systemType);

    public String getGlobalTicketKey(String type, int id);

    public int getUserFromTicket(String type, String ticket);

    public void setTicketExpireTime(String type, String ticket, int id, int seconds);

    public UserModel getUserByid(int id);


    public boolean verifyTicketOrUpdate(String type, String ticket);
    public boolean verifyToken(String type, String ticket);
    public UserModel loginViaPassword(String username, String password);
    public String creteTicket(String type, int id);
    public UserModel getUserModelByTicket(String type, String ticket);
}
