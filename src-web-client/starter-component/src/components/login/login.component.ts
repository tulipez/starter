import { html, LitElement } from 'lit';
import { state } from 'lit/decorators.js';
import { consume } from '@lit/context';
import '@shoelace-style/shoelace/dist/themes/light.css';
import '@shoelace-style/shoelace/dist/themes/dark.css';
import '@shoelace-style/shoelace/dist/components/button/button.js';
import '@shoelace-style/shoelace/dist/components/icon/icon.js';
import '@shoelace-style/shoelace/dist/components/input/input.js';
import '@shoelace-style/shoelace/dist/components/rating/rating.js';
import '@shoelace-style/shoelace/dist/components/alert/alert.js';
import styles from './login.styles.js';

import { UserService, userServiceContext } from '../../services/UserService.js';

export class TzLogin extends LitElement {
	
    static styles = styles;
    
    
    @consume({context: userServiceContext})
    @state()
	private userService?: UserService;
	
    onConnectionButtonClick() {
		// const button = this.renderRoot.querySelector('#connection_button') as HTMLElement;
  		// button.setAttribute("pending", "");
  		this.userService?.login();
    }
    
    render() {
        return html`
        <div class="bg-img">
        	<div class="appname">Starter</div>
        	<div>
        	
        		<sl-button
        			id="connection_button"
        			variant="primary"
        			size="large"
        			@click="${this.onConnectionButtonClick}"
        		>S'identifier</sl-button>
        		
			</div>
        </div>
        `;
    }
}
