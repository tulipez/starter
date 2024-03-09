import { css } from 'lit';

export default css`

	:host {
	    display: flex;
	    gap: 16px;
	    margin: 16px;
	}
	
	@media all and (max-width: 700px) and (hover: none) and (pointer: coarse),
		   all and (max-height: 700px) and (hover: none) and (pointer: coarse) {
			   
		:host {
    		flex-direction: column;
			flex-wrap: nowrap;
		}
	}
	
	@media not all and (max-width: 700px) and (hover: none) and (pointer: coarse),
		   not all and (max-height: 700px) and (hover: none) and (pointer: coarse) {
			   
		:host {
    		flex-direction: row;
			flex-wrap: wrap;
		}
	}
	
`;