'use strict';

// tag::vars[]
const React = require('react');
const client = require('./client');
// end::vars[]

// tag::app[]
class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {balloons: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/balloons'}).done(response => {
			this.setState({balloons: response.entity._embedded.balloons});
		});
	}

	render() {
		return (
			<balloonList balloons={this.state.balloons}/>
		)
	}
}
// end::app[]

// tag::balloon-list[]
class balloonList extends React.Component{
	render() {
		var balloons = this.props.balloons.map(balloon =>
			<balloon key={balloon._links.self.href} balloon={balloon}/>
		);
		return (
			<table>
				<tr>
					<th>Tracking Token</th>
					<th>Start Location</th>
					<th>End Location</th>
				</tr>
				{balloons}
			</table>
		)
	}
}
// end::balloon-list[]

// tag::balloon[]
class balloon extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.balloon.trackingToken}</td>
				<td>{this.props.balloon.startLocation}</td>
				<td>{this.props.balloon.endLocation}</td>
			</tr>
		)
	}
}
// end::balloon[]

// tag::render[]
React.render(
	<App />,
	document.getElementById('react')
)
// end::render[]

