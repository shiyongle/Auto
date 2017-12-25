/**
 * 
 * tree节点前端搜索混入器
 * 
 * @author ZJZ（447338871@qq.com）
 * @version 0.1 2015-2-3 下午2:19:10
 * 
 */
Ext.define('DJ.tools.tree.TreeFilterMixer', {

	filterByText : function(text) {

		this.filterBy(text, 'text');

	},

	/**
	 * 
	 * Filter the tree on a string, hiding all nodes expect those which match
	 * and their parents.
	 * 
	 * @param The
	 *            term to filter on.
	 * 
	 * @param The
	 *            field to filter on (i.e. 'text').
	 * 
	 */

	filterBy : function(text, by) {

		this.clearFilter();

		var view = this.getView();

		var me = this;

		var nodesAndParents = [];

		// Find the nodes which match the search term, expand them.

		// Then add them and their parents to nodesAndParents.

		this.getRootNode().cascadeBy(function(tree, view) {

			var currNode = this;

			if (currNode
					&& currNode.data[by]
					&& currNode.data[by].toString().toLowerCase().indexOf(text
							.toLowerCase()) > -1) {

				me.expandPath(currNode.getPath());

				while (currNode.parentNode) {

					nodesAndParents.push(currNode.id);

					currNode = currNode.parentNode;

				}

			}

		}, null, [me, view]);

		// Hide all of the nodes which aren't in nodesAndParents

		this.getRootNode().cascadeBy(function(tree, view) {

			var uiNode = view.getNodeByRecord(this);

			if (uiNode && !Ext.Array.contains(nodesAndParents, this.id)) {

				Ext.get(uiNode).setDisplayed('none');

			}

		}, null, [me, view]);

	},

	clearFilter : function() {

		var view = this.getView();

		this.getRootNode().cascadeBy(function(tree, view) {

			var uiNode = view.getNodeByRecord(this);

			if (uiNode) {

				Ext.get(uiNode).setDisplayed('table-row');

			}

		}, null, [this, view]);

	}

});