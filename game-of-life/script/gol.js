
//************** Grid **********************
function Grid(rows, columns) {
	this.numberOfRows = rows;
	this.gameOfLifeGrid = $('<div />')
	this.gameOfLifeGrid.addClass('gol-grid');

	this.rows = [];

	for (var i = 0; i < rows; i++) {
		this.rows[i] = new Row(columns);
		this.rows[i].appendTo(this.gameOfLifeGrid);
	};
}

Grid.prototype.appendTo = function (element) {
	element.append(this.gameOfLifeGrid);
}

Grid.prototype.evolve = function (rowIndex, cellIndex, fnEvolve) {
	var numberOfAdjacentLiveCells = this.liveCells(rowIndex - 1, cellIndex) + this.adjacentLiveCells(rowIndex, cellIndex) + this.liveCells(rowIndex + 1, cellIndex);
	var currentState = this.getState(rowIndex, cellIndex);
	return fnEvolve(numberOfAdjacentLiveCells, currentState);
}

Grid.prototype.getState = function (rowIndex, cellIndex) {
	return this.rows[rowIndex].getState(cellIndex);
}

Grid.prototype.liveCells = function (rowIndex, cellIndex) {
	if(rowIndex < 0 || rowIndex >= this.numberOfRows) {
		return 0;
	} else {
		return this.rows[rowIndex].liveCells(cellIndex);
	}
}

Grid.prototype.adjacentLiveCells = function (rowIndex, cellIndex) {
	if(rowIndex < 0 || rowIndex >= this.numberOfRows) {
		return 0;
	} else {
		return this.rows[rowIndex].adjacentLiveCells(cellIndex);
	}
}

Grid.prototype.changeState = function (state) {
	for (var i = 0; i < this.numberOfRows; i++) {
		this.rows[i].changeState(state[i]);
	}
}


//************** Row **********************
function Row(columns) {
	this.numberOfColumns = columns;
	this.gameOfLifeGridRow = $('<div />')
	this.gameOfLifeGridRow.addClass('gol-row');

	this.cells = [];

	for (var i = 0; i < columns; i++) {
		this.cells[i] = new Cell();
		this.cells[i].appendTo(this.gameOfLifeGridRow);
	};

}

Row.prototype.appendTo = function (element) {
	element.append(this.gameOfLifeGridRow);
}

Row.prototype.append = function (element) {
	this.gameOfLifeGridRow.append(element);
}

Row.prototype.liveCells = function (index) {
	return this.isAlive(index-1) + this.isAlive(index) + this.isAlive(index + 1);
}

Row.prototype.adjacentLiveCells = function (index) {
	return this.isAlive(index-1) + this.isAlive(index + 1);
}

Row.prototype.isAlive = function (index) {
	if(index < 0 || index >= this.numberOfColumns) {
		return 0;	
	} else if(this.cells[index].isAlive()) {
		return 1;
	} else {
		return 0;
	}

}

Row.prototype.getState = function (index) {
	return this.cells[index].isAlive();
}

Row.prototype.changeState = function (state) {
	for (var i = 0; i < this.numberOfColumns; i++) {
		if(state[i]) {
			this.cells[i].makeAlive();
		} else {
			this.cells[i].kill();
		}
	}
}

//************** Cell **********************

function Cell () {
	this.alive = false;
	var cell = this;

	this.gameOfLifeGridCell = $('<div />')
	this.gameOfLifeGridCell
		.addClass('gol-cell')
		.addClass('dead')
		.click({param : cell}, function (event) {
			event.data.param.toggle();
		});
}

Cell.prototype.appendTo = function (row) {
	row.append(this.gameOfLifeGridCell);
}

Cell.prototype.makeAlive = function() {
	this.alive = true;
	this.gameOfLifeGridCell.removeClass('dead');
	this.gameOfLifeGridCell.addClass('alive');
}

Cell.prototype.kill = function () {
	this.alive = false;
	this.gameOfLifeGridCell.removeClass('alive');
	this.gameOfLifeGridCell.addClass('dead');
}

Cell.prototype.toggle = function () {
	if(this.alive) {
		this.kill();
	} else {
		this.makeAlive();
	}
}

Cell.prototype.isAlive = function () {
	return this.alive;
}

//************** Game Of Life **********************
function GameOfLife(playGroundDiv, rows, columns) {
	this.gridRows = rows;
	this.gridColumns = columns;
	this.grid = new Grid(rows, columns);
	
	var playGround = $('#'+playGroundDiv);
	playGround.text("");

	this.grid.appendTo(playGround);
}

GameOfLife.prototype.go = function () {
	var newState = [];
	for (var i = 0; i < this.gridRows; i++) {
		newState[i] = [];
		for (var j = 0; j < this.gridColumns; j++) {
			newState[i][j] = this.grid.evolve(i, j, evolution);
		}	
	}
	this.grid.changeState(newState);
}


function evolution(count, state) {
	if(count < 2 || count > 3) {
		return false;
	} else if(count == 2) {
		return state;
	} else {
		return true;
	}
}
