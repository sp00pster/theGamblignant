package theGamblignant.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import theGamblignant.VriskaMod;
import theGamblignant.characters.TheGamblignant;

import static theGamblignant.VriskaMod.makeCardPath;

public class LightScreen extends AbstractVriskaCard {

    public static final String ID = VriskaMod.makeID(LightScreen.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheGamblignant.Enums.COLOR_COBALT;

    private static final int COST = 2;

    public LightScreen() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        magicNumber = 5;
        baseMagicNumber = magicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int blockroll = 0;
        for (int i = 0; i < magicNumber; i++) {
            blockroll += roll(6,'s');
        }
        baseBlock = blockroll;
        applyPowersToBlock();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    public void applyPowers() {
        super.applyPowers();
        int addedblock = 0;
        if (AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {addedblock += AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;}
        if (!this.upgraded) {
            if (addedblock > 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[0] + addedblock + cardStrings.EXTENDED_DESCRIPTION[4];
            } else if (addedblock < 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[1] + addedblock + cardStrings.EXTENDED_DESCRIPTION[4];
            } else {this.rawDescription = cardStrings.DESCRIPTION;}
        } else {
            if (addedblock > 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[2] + addedblock + cardStrings.EXTENDED_DESCRIPTION[5];
            } else if (addedblock < 0) {
                this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[3] + addedblock + cardStrings.EXTENDED_DESCRIPTION[5];
            } else {this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;}
        }
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }
}
