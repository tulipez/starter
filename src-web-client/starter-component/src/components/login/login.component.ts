import { html, LitElement } from 'lit';
import '@shoelace-style/shoelace/dist/themes/light.css';
import '@shoelace-style/shoelace/dist/themes/dark.css';
import '@shoelace-style/shoelace/dist/components/button/button.js';
import '@shoelace-style/shoelace/dist/components/icon/icon.js';
import '@shoelace-style/shoelace/dist/components/input/input.js';
import '@shoelace-style/shoelace/dist/components/rating/rating.js';
import '@shoelace-style/shoelace/dist/components/alert/alert.js';
import styles from './login.styles.js';

export class TzLogin extends LitElement {
	
    static styles = styles;
    
    render() {
        return html`
        <div class="bg-img">
        	<div class="appname">Starter</div>
        	<div>
        		<sl-button
        			id="connection_button"
        			variant="primary"
        			size="large"
        			@click="${() => {window.location.href='/login';}}"
        		>S'identifier</sl-button>
			</div>
        </div>
        `;
    }
}
