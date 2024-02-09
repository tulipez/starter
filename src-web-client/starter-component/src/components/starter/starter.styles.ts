import { css } from 'lit';

export default css`

	:host {
		display: block;
		width: 100%;
		height: 100%;
	}
	
	.debug {
		--sl-color-primary-50: var(--sl-color-purple-50);
		--sl-color-primary-100: var(--sl-color-purple-100);
		--sl-color-primary-200: var(--sl-color-purple-200);
		--sl-color-primary-300: var(--sl-color-purple-300);
		--sl-color-primary-400: var(--sl-color-purple-400);
		--sl-color-primary-500: var(--sl-color-purple-500);
		--sl-color-primary-600: var(--sl-color-purple-600);
		--sl-color-primary-700: var(--sl-color-purple-700);
		--sl-color-primary-800: var(--sl-color-purple-800);
		--sl-color-primary-900: var(--sl-color-purple-900);
		--sl-color-primary-950: var(--sl-color-purple-950);
	}
	
	.application {
		width: 100%;
	    height: 100%;
	    display: flex;
        flex-direction: column;
    	align-items: stretch;
	    background-color: var(--sl-panel-background-color);
	}
	
	.top-bar {
		display: flex;
	    flex-direction: row;
	    flex-wrap: nowrap;
	    align-items: center;
	    justify-content: space-between;
	}
	
	.app-content {
		flex: 1;
	}
	
	login-component {
		width: 100%;
		height: 100%;
	}
	
`;