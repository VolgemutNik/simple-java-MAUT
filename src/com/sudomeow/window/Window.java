package com.sudomeow.window;

import com.sudomeow.maut.exceptions.MAUTCalculationException;
import com.sudomeow.maut.MAUTNode;
import com.sudomeow.maut.functions.ExponentialFunction;
import com.sudomeow.maut.functions.FallingExponentialFunction;
import com.sudomeow.maut.functions.LinearFunction;
import com.sudomeow.maut.functions.TableFunction;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame {

    private static final Dimension DIM = new Dimension(1200, 720);

    private final JPanel treePanel = new JPanel();
    private final JPanel inputPanel = new JPanel();

    private JTree jTree;
    private DefaultTreeModel treeModel;
    private MAUTNode mautRoot;

    public Window() {
        setPreferredSize(DIM);
        setMinimumSize(DIM);
        setMaximumSize(DIM);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("MOS Vaja 3");

        final JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(null);
        add(wrapperPanel, BorderLayout.CENTER);

        treePanel.setBackground(Color.DARK_GRAY);
        treePanel.setBounds(0, 0, 600, 720);
        treePanel.setLayout(new BorderLayout());

        JPanel treeButtonPanel = new JPanel();
        treeButtonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add node");
        addButton.addActionListener(action -> {
            MAUTNode node = (MAUTNode) jTree.getLastSelectedPathComponent();
            if (node == null) return;

            String nodeName = JOptionPane.showInputDialog("Enter the name of the new Node.");
            if (nodeName != null) {
                treeModel.insertNodeInto(new MAUTNode(node, nodeName), node, node.getChildCount());
                System.out.println("Added new node with name: " + nodeName + " to " + node.getMautData().getNodeName()); //TODO: remove debug
            }
        });
        treeButtonPanel.add(addButton);

        JButton removeButton = new JButton("Remove node");
        removeButton.addActionListener(action -> {
            TreePath currentSelection = jTree.getSelectionPath();
            if (currentSelection != null) {
                MAUTNode selectedNode = (MAUTNode) currentSelection.getLastPathComponent();
                MAUTNode selectedParent = (MAUTNode) selectedNode.getParent();
                if (selectedParent != null) {
                    treeModel.removeNodeFromParent(selectedNode);
                    System.out.println("Removed node with name: " + selectedNode.getMautData().getNodeName());//TODO: remove debug
                }
            }

        });
        treeButtonPanel.add(removeButton);

        treePanel.add(treeButtonPanel, BorderLayout.PAGE_START);

        wrapperPanel.add(treePanel);

        inputPanel.setBackground(Color.LIGHT_GRAY);
        inputPanel.setBounds(600, 0, 600, 720);
        wrapperPanel.add(inputPanel);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    MAUTNode root = (MAUTNode) treeModel.getRoot();
                    System.out.println("[!] Expected Result: around 0.619");
                    System.out.println("[*] Result: " + root.calculate("TEST"));
                } catch (MAUTCalculationException mautCalculationException) {
                    mautCalculationException.printStackTrace();
                }
            }
        });

        init();
    }

    private void init() {
        //TODO: add serialized objects check
        //TODO: "Would you like to load saved data?" option pane
        final String rootName = JOptionPane.showInputDialog("Please enter the name of the root decision element.");
        if (rootName != null) {
            mautRoot = new MAUTNode(null, rootName);

            treeModel = new DefaultTreeModel(mautRoot);
            treeModel.addTreeModelListener(new CustomTreeModelListener());

            jTree = new JTree(treeModel);
            jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
            jTree.addTreeSelectionListener(event -> {
                MAUTNode node = (MAUTNode) jTree.getLastSelectedPathComponent();
                if (node == null) return;
                System.out.println("Selected node: " + node.getMautData().getNodeName());

                //TODO: Display edit page on the right
                JPanel editPanel = new JPanel();
                editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.PAGE_AXIS));
                JPanel mainContent = new JPanel();


                JButton saveButton = new JButton("Save Changes");
                saveButton.addActionListener(action -> {
                    //TODO: Save changes button implementation
                });
                editPanel.add(mainContent, BorderLayout.CENTER);
                editPanel.add(saveButton, BorderLayout.PAGE_END);

                clearAndAddComponentToPanel(inputPanel, editPanel);
            });

            /*  Calculated by hand, the values used her produce the result 0.619
             *  root:
             *      test1(0,5):
             *          test11(0,7) -> LinearFunction
             *          test12(0,3) -> LinearFunction
             *      test2(0,5):
             *          test21(0,33) -> ExponentialFunction
             *          test22(0,33) -> FallingExponentialFunction
             *          test23(0,33) -> TableFunction
             */
            MAUTNode test1 = new MAUTNode(mautRoot,"test1");
            test1.getMautData().setWeight(0.5d);
            treeModel.insertNodeInto(test1, mautRoot, mautRoot.getChildCount());


            MAUTNode test11 = new MAUTNode(test1,"test11");
            test11.getMautData().setWeight(0.7d);
            test11.getMautData().setUsefulnessFunction(new LinearFunction(30.0d, 70.0d));
            test11.getMautData().getMappedValues().put("TEST", "56.0");
            treeModel.insertNodeInto(test11, test1, test1.getChildCount());

            MAUTNode test12 = new MAUTNode(test1,"test12");
            test12.getMautData().setWeight(0.3d);
            test12.getMautData().setUsefulnessFunction(new LinearFunction(30.0d, 60.0d));
            test12.getMautData().getMappedValues().put("TEST", "59.0");
            treeModel.insertNodeInto(test12, test1, test1.getChildCount());


            MAUTNode test2 = new MAUTNode(mautRoot,"test2");
            test2.getMautData().setWeight(0.5);
            treeModel.insertNodeInto(test2, mautRoot, mautRoot.getChildCount());

            MAUTNode test21 = new MAUTNode(test2, "test21");
            test21.getMautData().setWeight(0.33d);
            test21.getMautData().setUsefulnessFunction(new ExponentialFunction(2, 100.0d));
            test21.getMautData().getMappedValues().put("TEST", "45.0d");
            treeModel.insertNodeInto(test21, test2, test2.getChildCount());

            MAUTNode test22 = new MAUTNode(test2, "test22");
            test22.getMautData().setWeight(0.33d);
            test22.getMautData().setUsefulnessFunction(new FallingExponentialFunction(2, 100.0d));
            test22.getMautData().getMappedValues().put("TEST", "45.0d");
            treeModel.insertNodeInto(test22, test2, test2.getChildCount());

            MAUTNode test23 = new MAUTNode(test2, "test23");
            test23.getMautData().setWeight(0.33d);
            TableFunction tableFunction = new TableFunction();
            tableFunction.map("TEST", 0.5d);
            tableFunction.map("TEST1", 0.6d);
            test23.getMautData().setUsefulnessFunction(tableFunction);
            test23.getMautData().getMappedValues().put("TEST", "TEST");
            treeModel.insertNodeInto(test23, test2, test2.getChildCount());


            treePanel.add(jTree, BorderLayout.CENTER);
        }

        setVisible(true);
    }

    /**
     * Forces JFrame to redraw all the sub-components.
     */
    public void updateWindow() {
        this.invalidate();
        this.validate();
        this.repaint();
    }

    private void clearAndAddComponentToPanel(JPanel panel, Component comp) {
        for (Component c : panel.getComponents()) {
            panel.remove(c);
        }
        panel.add(comp);
        panel.revalidate();
        panel.repaint();
    }

    /**
     * Takes care of refreshing
     */
    public class CustomTreeModelListener implements TreeModelListener {

        @Override
        public void treeNodesInserted(TreeModelEvent e) {
            jTree.expandPath(e.getTreePath());
        }

        @Override
        public void treeNodesChanged(TreeModelEvent e) {
        }

        @Override
        public void treeNodesRemoved(TreeModelEvent e) {
        }

        @Override
        public void treeStructureChanged(TreeModelEvent e) {
            jTree.expandPath(e.getTreePath());
        }
    }

}
