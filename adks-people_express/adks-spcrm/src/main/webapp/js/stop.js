    document.onkeydown = function() {  
        
        /*keyCod从113-123分别禁用了F2-F12键,13禁用了回车键(Enter),8禁用了退格键(BackSpace)*/ 
	
		if(event.keyCode == 113) { 
			event.keyCode = 0;
			return false;
		} 
		if(event.keyCode == 114) { 
			event.keyCode = 0;
			return false;    
		} 
		if(event.keyCode == 115) { 
			event.keyCode = 0;
			return false;    
		} 
        if (event.keyCode == 116) {  
            event.keyCode = 0;
            return false;  
        }
		if(event.keyCode == 117) { 
			event.keyCode = 0;
			return false;    
		} 
		if(event.keyCode == 118) { 
			event.keyCode = 0;
			return false;    
		} 
		if(event.keyCode == 119) { 
			event.keyCode = 0;
			return false;    
		} 
		if(event.keyCode == 120) { 
			event.keyCode = 0;
			return false;    
		} 
		if(event.keyCode == 121) { 
			event.keyCode = 0;
			return false;    
		} 
		if(event.keyCode == 122) { 
			event.keyCode = 0;
			return false;    
		} 
		if(event.keyCode == 123) { 
			event.keyCode = 0;
			return false;    
		} 
		if (event.keyCode == 123) {  
            event.keyCode = 0;
            return false;  
        }
		
		if(event.keyCode == 13) { 
			event.keyCode = 0;
			return false;
		} 
		if(event.keyCode == 8) { 
			event.keyCode = 0;
			return false;
		} 
    }  
    document.oncontextmenu = function() {  
        return  false;  
    }