Ext.define('DJ.Base.Grid.GridRowNum', {
	extend : 'Ext.grid.RowNumberer',
	header:'序号',
	width : 35,
	alias: 'widget.rownum',
	stateId : 'gridRowNum',
	
	renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
		if (store.lastOptions != null) {
			var pageindex = store.lastOptions.start;
			return pageindex + rowIndex + 1;
		} else {
			return rowIndex + 1;
		}
	}
});