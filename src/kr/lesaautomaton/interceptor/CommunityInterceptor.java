package kr.lesaautomaton.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import kr.lesaautomaton.persistence.domain.Board;
import kr.lesaautomaton.presentation.CommunityAction;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;

public class CommunityInterceptor extends SessionPreprocessInterceptor {
	
	private String avaliable;
	
	@Override
	protected boolean applyInterceptor(ActionInvocation invocation) {
		// TODO Auto-generated method stub
		if(super.applyInterceptor(invocation)){
			
			HttpServletRequest request = ServletActionContext.getRequest();
			
			CommunityAction action = null;
			Board board = null;
			String boardId = request.getParameter("boardId");
			
			if(StringUtils.isNotBlank(boardId)){
				
				Map<String, Board> boardIdMap = (Map<String, Board>)ServletActionContext.getServletContext().getAttribute("boardIdMap");
				
				if(boardIdMap.keySet().contains(boardId)){
					
					board = (Board)boardIdMap.get(boardId);
					
					if(StringUtils.isNotEmpty(avaliable)){
					
						try{
							setGroupCodes(BeanUtils.getArrayProperty(board, avaliable));
						}catch(Exception e){
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					
					}
					
					action = ((CommunityAction)invocation.getAction());
					action.setPageId(String.format("/community/%s", boardId));
					action.setBoard(board);
					
					return true;

					
				}else{
					
					throw new RuntimeException("Invalid value in parameter [boardId].");
					
				}
				
			}else{
				
				//return ActionSupport.ERROR;
				throw new RuntimeException("Required parameter [boardId] is missing.");
				
			}			
			
		}
		
		return false;
		
	}

	public void setAvaliable(String avaliable) {
		this.avaliable = avaliable;
	}
	

}
