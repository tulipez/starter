import { css } from 'lit';

export default css`

	:host {
	}
	.bg-img {
		background-image: url("https://picsum.photos/1200/1024");
		width: 100%;
		height: 100%;
		background-position: center;
		background-repeat: no-repeat;
		background-size: cover;
		display: flex;
		justify-content: center;
		position: relative;
	}
	.appname {
		position: absolute;
	    font-size: 18px;
	    font-weight: bold;
		color: white;
	    left: 20px;
	    top: 10px;
	}
	sl-button {
		top: 61.8dvh;
	}
	
`;