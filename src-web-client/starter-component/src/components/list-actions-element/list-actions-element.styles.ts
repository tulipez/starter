import { css } from 'lit';

	/*
	sl-card [slot='footer'] {
	    display: flex;
	    justify-content: space-between;
	    align-items: center;
  	}
    */
   
export default css`

	:host {
	}
	
	.card-content {
	    display: flex;
	    justify-content: space-between;
	    align-items: center;
  	}
	
	@media all and (max-width: 700px) and (hover: none) and (pointer: coarse),
		   all and (max-height: 700px) and (hover: none) and (pointer: coarse) {
			   
		:host {
    		flex: 1;
		}
		
		sl-card {
			width: 100%;
		}
	}
	
	@media not (all and (max-width: 700px) and (hover: none) and (pointer: coarse)),
		   not (all and (max-height: 700px) and (hover: none) and (pointer: coarse)) {
			   
		:host {
		}
	}
	
`;