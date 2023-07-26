package theGamblignant.cards;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theGamblignant.VriskaMod;
import theGamblignant.actions.RollNumberEffect;
import theGamblignant.actions.TimedVFXAction;
import theGamblignant.powers.*;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractVriskaCard extends CustomCard {

        public static final Logger logger = LogManager.getLogger(VriskaMod.class.getName()); //this is for testing purposes, you can remove this eventually

    public AbstractVriskaCard(final String id,
                              final String img,
                              final int cost,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

        // Set all the things to their default values.
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
    }

    //this roll function was inspired by that of Downfall's Snecko

    public static int roll(int faces, char purpose) {
        //purpose is used to store information about the card played. this was once used for powers that only buff attack rolls or skill rolls, but these were removed. keeping the parameter just in case tho
        int luckAmt = 0;
        int result;
        int max;
        int min;
        AbstractPlayer p = AbstractDungeon.player;

        if (p.hasPower(LuckPower.POWER_ID)) {
            p.getPower(LuckPower.POWER_ID).flash();
            luckAmt += p.getPower(LuckPower.POWER_ID).amount;
        }
        //TODO on cards that roll multiple dice, changes to vim and curse do NOT update in between a single card's rolls.
        if (p.hasPower(VimPower.POWER_ID) && purpose != 'm') { //"m" is for "multi", where one card rolls multiple times. these cards didnt work properly with vim and curse so im going with a sort of messy solution
            luckAmt += p.getPower(VimPower.POWER_ID).amount;
            p.getPower(VimPower.POWER_ID).flash();
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, VimPower.POWER_ID));
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p, p, "Vim",100));
            logger.info("vim triggered");
        }
        if (p.hasPower(CursePower.POWER_ID) && purpose != 'm') {
            luckAmt -= p.getPower(CursePower.POWER_ID).amount;
            p.getPower(CursePower.POWER_ID).flash();
            //AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(p, p, CursePower.POWER_ID));
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(p,p,"Curse", 1));
        }
        if (p.hasRelic("Bionic Boundbeast")) {
            luckAmt += 1;
        }


        logger.info("luck going into this roll: "+luckAmt); //you can remove this eventually

        max = faces;
        min = 1;
        result = AbstractDungeon.cardRandomRng.random(min, max);

        logger.info("rolled a "+result+" before luck");

        result += luckAmt;
        if (result > max) {result = max;}
        if (result < 1) {result = 1;}

        if ((faces == 8 && p.hasPower(AncestralAwakeningPower.POWER_ID))) {
            result = 8;
        }

        if (result == max) {
            AbstractDungeon.actionManager.addToTop(new TimedVFXAction(new RollNumberEffect(p.dialogX+25F, p.dialogY, result+"!")));
        } else {
            AbstractDungeon.actionManager.addToTop(new TimedVFXAction(new RollNumberEffect(p.dialogX+25F, p.dialogY, Integer.toString(result))));

        }
        logger.info("final roll: "+result);

        if (result == 1 && p.hasRelic("Dim Bulb")) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new VimPower(p, 2), 2, true));
        }

        return result;
    }
}