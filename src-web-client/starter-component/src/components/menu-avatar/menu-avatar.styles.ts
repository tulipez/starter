import { css } from 'lit';

export default css`

	:host {
	}
	.avatar-button {
		all: unset;
		cursor: pointer;
	    margin: 20px 20px;
	}
	.avatar-button sl-avatar {
		--size: 32px;
	}
	.avatar-button:focus sl-avatar::part(base) {
		outline: none;
	}
	.avatar-button:focus-visible sl-avatar::part(base) {
		outline: var(--sl-focus-ring);
    	outline-offset: 2px;
	}
	.avatar-button:hover sl-avatar::part(base) {
		outline: solid 2px var(--sl-color-primary-300);
	}
	.avatar-button:active sl-avatar::part(base) {
		outline: solid 2px var(--sl-color-primary-400);
	}
	
`;