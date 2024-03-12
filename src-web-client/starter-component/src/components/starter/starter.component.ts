import { html, LitElement } from 'lit';
import { state } from 'lit/decorators.js';
import { provide } from '@lit/context';
import '@shoelace-style/shoelace/dist/themes/light.css';
import '@shoelace-style/shoelace/dist/themes/dark.css';
import '@shoelace-style/shoelace/dist/components/button/button.js';
import '@shoelace-style/shoelace/dist/components/icon/icon.js';
import '@shoelace-style/shoelace/dist/components/input/input.js';
import '@shoelace-style/shoelace/dist/components/rating/rating.js';
import '@shoelace-style/shoelace/dist/components/alert/alert.js';
import '@shoelace-style/shoelace/dist/components/avatar/avatar.js';
import '@shoelace-style/shoelace/dist/components/menu/menu.js';
import '@shoelace-style/shoelace/dist/components/menu-item/menu-item.js';
import '@shoelace-style/shoelace/dist/components/divider/divider.js';
import '@shoelace-style/shoelace/dist/components/select/select.js';
import '@shoelace-style/shoelace/dist/components/option/option.js';
import { registerIconLibrary } from '@shoelace-style/shoelace/dist/utilities/icon-library.js';

import styles from './starter.styles.js';

// import { IS_MOBILE, MatchMediaController } from '../../controllers/MatchMedia.js';
import { HttpClient } from '../../HttpClient.js';
import { WorkspaceService, workspaceServiceContext } from '../../services/WorkspaceService.js';
import { ActionService, actionServiceContext } from '../../services/ActionService.js';
import '../login/login.js';
import '../menu-avatar/menu-avatar.js';
import '../menu-general/menu-general.js';
import '../list-actions/list-actions.js';

registerIconLibrary('lucide', {
	resolver: name => `https://cdn.jsdelivr.net/npm/lucide-static/icons/${name}.svg`
});

export class TzStarter extends LitElement {
	
	static styles = styles;
	
	private httpClient: HttpClient;
	
	@provide({context: workspaceServiceContext})
	private workspaceService: WorkspaceService;
	
	@provide({context: actionServiceContext})
	private actionService: ActionService;
	
	@state()
	initialized: boolean = false;
	
	/*
	mobileController = new MatchMediaController(this, IS_MOBILE);
	isMobile() {
		return this.mobileController.matches;
	}
	*/
	
	constructor() {
		super();
		
		this.httpClient = new HttpClient();
		this.workspaceService = new WorkspaceService(this.httpClient);
		this.actionService = new ActionService(this.httpClient, this.workspaceService);
		
		this.workspaceService.loadCurrentWorkspace().then(() => {
			this.initialized = true;
		}).catch((error) => {
			console.error(error);
		});
	}
		
	render() {
		return html`
		${!this.initialized ?
	      	html``:
	      	html`${!this.workspaceService.currentWorkspace ?
		      	html`<tz-login></tz-login>`:
		      	html`
		      	<div class="application">
					<div class="top-bar">
						<tz-menu-general></tz-menu-general>
						<tz-menu-avatar></tz-menu-avatar>
					</div>
					<div class="app-content">
						<div class="content-header">
							<sl-button variant="text" size="large">
							  <sl-icon slot="prefix"
								name="calendar3"
								style="font-size: 20px;"
							  ></sl-icon>
							  Aujourd'hui
							</sl-button>
						</div>
						<tz-list-actions></tz-list-actions>
					</div>
				</div>`
	      	}`
      	}`;
	}
}